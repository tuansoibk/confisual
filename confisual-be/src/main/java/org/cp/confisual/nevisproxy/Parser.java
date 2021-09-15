/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy;

import org.cp.confisual.ParserException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.cp.confisual.util.XmlUtils.getDocument;
import static org.cp.confisual.util.XmlUtils.getElementsFromDoc;

public class Parser {

  private Parser() {
  }

  public static List<UrlPattern> parse(File configFile) throws ParserException, IOException {
    return parse(getDocument(configFile));
  }

  public static List<UrlPattern> parse(String xmlConfig) throws ParserException {
    return parse(getDocument(xmlConfig));
  }

  private static List<UrlPattern> parse(Document doc) {
    Map<String, Filter> filters = getElementsFromDoc(doc, ".//filter")
        .stream()
        .map(Parser::parseFilter)
        .distinct()
        .collect(Collectors.toMap(Filter::getName, filter -> filter));

    Map<String, Servlet> servlets = getElementsFromDoc(doc, ".//servlet")
        .stream()
        .map(Parser::parseServlet)
        .distinct()
        .collect(Collectors.toMap(Servlet::getName, servlet -> servlet));

    List<FilterMapping> filterMappings = getElementsFromDoc(doc, ".//filter-mapping")
        .stream()
        .map(Parser::parseFilterMapping)
        .filter(filterMapping -> filterMapping.getUrlPattern() != null)
        .collect(Collectors.toList());

    List<ServletMapping> servletMappings = getElementsFromDoc(doc, ".//servlet-mapping")
        .stream()
        .map(Parser::parseServletMapping)
        .collect(Collectors.toList());

    return linkUrlPatterns(filters, servlets, filterMappings, servletMappings);
  }

  private static List<UrlPattern> linkUrlPatterns(Map<String, Filter> filters, Map<String, Servlet> servlets,
                                                  List<FilterMapping> filterMappings,
                                                  List<ServletMapping> servletMappings) {
    List<String> patterns = Stream.concat(
        filterMappings.stream().map(FilterMapping::getUrlPattern),
        servletMappings.stream().map(ServletMapping::getUrlPattern))
          .distinct()
          .collect(Collectors.toList());

    List<UrlPattern> urlPatterns = new ArrayList<>();
    for (String pattern : patterns) {
      UrlPattern urlPattern = new UrlPattern(pattern);
      List<Filter> mappedFilters = filterMappings
          .stream()
          .filter(filterMapping -> filterMapping.includePattern(pattern))
          .map(filterMapping -> filters.get(filterMapping.getFilterName()))
          .collect(Collectors.toList());

      urlPattern.setFilters(mappedFilters);
      urlPatterns.add(urlPattern);

      Servlet mappedServlet = null;
      int longestMatch = 0;
      for (ServletMapping servletMapping : servletMappings) {
        int matchLen = servletMapping.calculateMatchingLength(pattern);
        if (matchLen > longestMatch) {
          longestMatch = matchLen;
          mappedServlet = servlets.get(servletMapping.getServletName());
        }
      }

      urlPattern.setServlet(mappedServlet);
    }

    return urlPatterns;
  }

  private static Filter parseFilter(Element element) {
    return new Filter(element.getChildText("filter-name"), element.getChildText("filter-class"));
  }

  private static Servlet parseServlet(Element element) {
    return new Servlet(element.getChildText("servlet-name"), element.getChildText("servlet-class"));
  }

  private static FilterMapping parseFilterMapping(Element element) {
    return new FilterMapping(element.getChildText("filter-name"), element.getChildText("url-pattern"));
  }

  private static ServletMapping parseServletMapping(Element element) {
    return new ServletMapping(element.getChildText("servlet-name"), element.getChildText("url-pattern"));
  }
}

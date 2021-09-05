package org.cp.confisual.nevisauth;

import org.cp.confisual.ParserException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.XMLConstants;

public class Parser {

  public static List<Domain> parse(File configFile) throws ParserException {
    Document doc;
    try {
      SAXBuilder sax = new SAXBuilder(); // NOSONAR: external DTD loading is disabled
      sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      doc = sax.build(configFile);
    } catch (IOException | JDOMException e) {
      throw new ParserException("Unable to parse nevisAuth config file", e);
    }

    return parse(doc);
  }

  public static List<Domain> parse(String configXML) throws ParserException {
    Document doc;
    try {
      SAXBuilder sax = new SAXBuilder();
      sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
      sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      doc = sax.build(new StringReader(configXML));
    } catch (IOException | JDOMException e) {
      throw new ParserException("Unable to parse nevisAuth config file", e);
    }

    return parse(doc);
  }

  private static List<Domain> parse(Document doc) {
    List<Domain> domains = getElementsFromDoc(doc, ".//Domain")
        .stream()
        .map(Parser::parseDomain)
        .collect(Collectors.toList());

    Map<String, AuthState> allAuthStates = getElementsFromDoc(doc, ".//AuthState")
        .stream()
        .map(Parser::parseAuthState)
        .collect(Collectors.toMap(AuthState::getName, state -> state));

    linkDomainsAndAuthStates(domains, allAuthStates);

    return domains;
  }

  private static void linkDomainsAndAuthStates(List<Domain> domains, Map<String, AuthState> allAuthStates) {
    for (AuthState authState : allAuthStates.values()) {
      for (Transition transition : authState.getTransitions()) {
        transition.setAuthState(allAuthStates.getOrDefault(transition.getAuthStateName(), AuthState.NOT_EXISTING));
      }
    }

    domains.forEach(domain -> linkDomainAuthStates(domain, allAuthStates));
  }

  private static void linkDomainAuthStates(Domain domain, Map<String, AuthState> allAuthStates) {
    for (Entry entry : domain.getEntries()) {
      AuthState authState = allAuthStates.getOrDefault(entry.getAuthStateName(), AuthState.NOT_EXISTING);
      entry.setAuthState(authState);
    }

    // trace through all domain's entries and auth state transitions to collect all auth states that belong to this domain
    Queue<AuthState> authStateQ = domain.getEntries()
                                        .stream()
                                        .map(Entry::getAuthState)
                                        .collect(Collectors.toCollection(LinkedList::new));
    while (!authStateQ.isEmpty()) {
      AuthState authState= authStateQ.poll();
      if (domain.getAuthStates().containsKey(authState.getName())) {
        continue;
      }

      domain.addAuthState(authState);
      for (Transition transition : authState.getTransitions()) {
        authStateQ.add(transition.getAuthState());
      }
    }
  }

  private static Domain parseDomain(Element element) {
    Domain domain = new Domain(element.getAttributeValue("name"));
    // get domain entries
    List<Element> entryElements = element.getChildren("Entry");
    parseDomainEntries(entryElements).forEach(domain::addEntry);

    return domain;
  }

  private static List<Entry> parseDomainEntries(List<Element> entryElements) {
    List<Entry> entries = new ArrayList<>();
    for (Element entry : entryElements) {
      String method = entry.getAttributeValue("method");
      String authStateName = entry.getAttributeValue("state");
      String selector = entry.getAttributeValue("selector") != null ? entry.getAttributeValue("selector") : "/" ;
      entries.add(new Entry(method, authStateName, selector));
    }

    return entries;
  }

  private static AuthState parseAuthState(Element stateElement) {
    AuthState authState = new AuthState(stateElement.getAttributeValue("name"), stateElement.getAttributeValue("class"));

    List<Element> conditions = stateElement.getChildren("ResultCond");
    for (Element condition : conditions) {
      String name = condition.getAttributeValue("name");
      String nextState = condition.getAttributeValue("next");
      Transition transition = new Transition(name, nextState);
      authState.addTransition(transition);
    }

    return authState;
  }

  private static List<Element> getElementsFromDoc(Document doc, String xpath) {
    return XPathFactory.instance()
                       .compile(xpath, Filters.element())
                       .evaluate(doc);
  }
}

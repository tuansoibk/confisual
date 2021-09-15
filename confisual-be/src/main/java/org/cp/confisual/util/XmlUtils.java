package org.cp.confisual.util;

import org.apache.commons.io.FileUtils;
import org.cp.confisual.ParserException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class XmlUtils {
  private static SAXBuilder sax;
  private static XPathFactory xPathFactory;

  static {
    sax = new SAXBuilder();
    sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    sax.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
    sax.setFeature("http://xml.org/sax/features/external-general-entities", false);
    sax.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    sax.setExpandEntities(false);

    xPathFactory = XPathFactory.instance();
  }

  private XmlUtils() {}

  public static Document getDocument(File xmlFile) throws ParserException, IOException {
    String xmlContent = FileUtils.readFileToString(xmlFile, StandardCharsets.UTF_8);
    return getDocument(xmlContent);
  }

  public static Document getDocument(String xmlContent) throws ParserException {
    // remove DOCTYPE declaration as we have set disallow-doctype-decl = true
    String contentToParse = xmlContent.replaceAll("<!DOCTYPE[^>]*+>", "");
    try {
      return sax.build(new StringReader(contentToParse));
    } catch (IOException | JDOMException e) {
      throw new ParserException("Unable to parse XML config", e);
    }
  }

  public static List<Element> getElementsFromDoc(Document doc, String xpath) {
    return xPathFactory
        .compile(xpath, Filters.element())
        .evaluate(doc);
  }
}

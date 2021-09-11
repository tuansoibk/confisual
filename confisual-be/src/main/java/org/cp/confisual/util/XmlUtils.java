package org.cp.confisual.util;

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
import java.util.List;

public class XmlUtils {
  private static SAXBuilder sax;
  private static XPathFactory xPathFactory;

  static {
    sax = new SAXBuilder(); // NOSONAR: external DTD loading is disabled
    sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    sax.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

    xPathFactory = XPathFactory.instance();
  }

  private XmlUtils() {}

  public static Document getDocument(File xmlFile) throws ParserException {
    try {
      return sax.build(xmlFile);
    } catch (IOException | JDOMException e) {
      throw new ParserException("Unable to parse config file", e);
    }
  }

  public static Document getDocument(String xmlConfig) throws ParserException {
    try {
      return sax.build(new StringReader(xmlConfig));
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

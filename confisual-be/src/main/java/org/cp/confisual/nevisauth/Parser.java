package org.cp.confisual.nevisauth;

import org.cp.confisual.ParserException;
import org.cp.confisual.util.XmlUtils;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.cp.confisual.util.XmlUtils.getElementsFromDoc;

public class Parser {

  private Parser() {}

  public static List<Domain> parse(File configFile) throws ParserException {
    return parse(XmlUtils.getDocument(configFile));
  }

  public static List<Domain> parse(String xmlConfig) throws ParserException {
    return parse(XmlUtils.getDocument(xmlConfig));
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
}

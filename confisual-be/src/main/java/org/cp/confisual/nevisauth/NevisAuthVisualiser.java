package org.cp.confisual.nevisauth;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.cp.confisual.ParserException;
import org.cp.confisual.VisualisationException;
import org.cp.confisual.util.PlantUmlUtils;

public class NevisAuthVisualiser {

  public Map<String, String> visualiseDomains(File esauthXmlFile) throws VisualisationException {
    List<Domain> domains;
    try {
      domains = Parser.parse(esauthXmlFile);
    }
    catch (ParserException | IOException e) {
      throw new VisualisationException("Can't parse nevisAuth config file", e);
    }

    return visualize(domains);
  }

  public Map<String, String> visualiseDomains(String esauthXmlContent) throws VisualisationException {
    List<Domain> domains;
    try {
      domains = Parser.parse(esauthXmlContent);
    }
    catch (ParserException e) {
      throw new VisualisationException("Can't parse nevisAuth string", e);
    }

    return visualize(domains);
  }


  private Map<String, String> visualize(List<Domain> domains) throws VisualisationException {
    if (domains.isEmpty()) {
      throw new VisualisationException("nevisAuth file doesn't have any domains");
    }

    Map<String, String> umlSources = domains
        .stream()
        .map(domain -> {
          PlantUmlSourceBuilder visitor = new PlantUmlSourceBuilder();
          domain.accept(visitor);
          return Map.entry(domain.getName(), visitor.getSource());
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return PlantUmlUtils.generateImages(umlSources);
  }
}

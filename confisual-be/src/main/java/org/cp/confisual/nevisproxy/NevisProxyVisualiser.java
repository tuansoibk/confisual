package org.cp.confisual.nevisproxy;

import org.cp.confisual.ParserException;
import org.cp.confisual.VisualisationException;
import org.cp.confisual.util.PlantUmlUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NevisProxyVisualiser {

  public Map<String, String> visualiseUrlPattern(File webXmlFile) throws VisualisationException {
    List<UrlPattern> urlPatterns;
    try {
      urlPatterns = Parser.parse(webXmlFile);
    }
    catch (ParserException e) {
      throw new VisualisationException("Can't parse nevisProxy config file", e);
    }

    return visualize(urlPatterns);
  }

  public Map<String, String> visualiseUrlPattern(String webXmlContent) throws VisualisationException {
    List<UrlPattern> urlPatterns;
    try {
      urlPatterns = Parser.parse(webXmlContent);
    }
    catch (ParserException e) {
      throw new VisualisationException("Can't parse nevisProxy config file", e);
    }

    return visualize(urlPatterns);
  }

  private Map<String, String> visualize(List<UrlPattern> urlPatterns) throws VisualisationException {
    if (urlPatterns.isEmpty()) {
      throw new VisualisationException("nevisProxy file doesn't have any url patterns");
    }

    Map<String, String> umlSources = urlPatterns
            .stream()
            .map(urlPattern -> {
              PlantUmlSourceBuilder builder = new PlantUmlSourceBuilder();
              builder.buildSource(urlPattern);
              return Map.entry(urlPattern.getPattern(), builder.getSource());
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return PlantUmlUtils.generateImages(umlSources);
  }
}

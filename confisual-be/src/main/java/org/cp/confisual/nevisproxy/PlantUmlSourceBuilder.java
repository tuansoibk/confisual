/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantUmlSourceBuilder {

  private List<String> sourceBuilder;
  
  public void buildSource(UrlPattern urlPattern) {
    sourceBuilder = new ArrayList<>();
    sourceBuilder.add("@startuml");
    sourceBuilder.add(":" + urlPattern.getPattern() + ";");
    urlPattern.getFilters().forEach(filter -> sourceBuilder.add(":" + filter.getName() + "/"));
    sourceBuilder.add(":" + urlPattern.getServlet().getName() + "|");
    sourceBuilder.add("@enduml");
  }

  public String getSource() {
    return sourceBuilder.stream().collect(Collectors.joining(System.lineSeparator()));
  }
}

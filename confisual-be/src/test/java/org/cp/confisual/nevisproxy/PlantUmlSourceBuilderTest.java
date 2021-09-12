package org.cp.confisual.nevisproxy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class PlantUmlSourceBuilderTest {

  @Test
  void shouldBuildActivityDiagram() {
    // given
    PlantUmlSourceBuilder builder = new PlantUmlSourceBuilder();
    UrlPattern urlPattern = new UrlPattern("/test/");
    urlPattern.setFilters(
            Arrays.asList(
                    new Filter("filter1", "filterclass1"),
                    new Filter("filter2", "filterclass2")
            )
    );
    urlPattern.setServlet(new Servlet("servlet", "servletclass"));

    // when
    builder.buildSource(urlPattern);

    // then
    String source = builder.getSource();
    assertTrue(source.contains(":/test/;"));
    assertTrue(source.contains(":filter1/"));
    assertTrue(source.contains(":filter2/"));
    assertTrue(source.contains(":servlet|"));
  }
}

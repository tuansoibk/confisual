package org.cp.confisual.nevisauth.integration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.cp.confisual.ParserException;
import org.cp.confisual.TestUtils;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.Parser;
import org.cp.confisual.nevisauth.PlantUmlVisitor;
import org.cp.confisual.plantuml.Visualiser;
import org.cp.confisual.plantuml.VisualizerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.cp.confisual.plantuml.Visualiser.IMG_EXTENSION;
import static org.junit.jupiter.api.Assertions.*;

class NevisAuthStateDiagramIntTest {

  // support objects
  private final Path testPath = Paths.get(System.getProperty("user.dir") + "/build/libs");
  private List<Domain> domains;

  @BeforeEach
  void setUp() throws ParserException {
    domains = new Parser().parse(TestUtils.getTestResourceFile("esauth4.xml"));
    domains.forEach(domain -> {
      try {
        Files.deleteIfExists(testPath.resolve(domain.getName() + IMG_EXTENSION));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  void canGenerateNevisAuthStateDiagram() throws VisualizerException {
    // when
    for (Domain domain : domains) {
      Visualiser.visualise(new PlantUmlVisitor(), domain, testPath);
    }

    // then
    domains.forEach(domain -> assertTrue(Files.exists(testPath.resolve(domain.getName() + IMG_EXTENSION))));
  }
}

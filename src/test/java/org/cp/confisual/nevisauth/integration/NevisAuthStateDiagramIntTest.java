package org.cp.confisual.nevisauth.integration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.cp.confisual.ParserException;
import org.cp.confisual.TestUtils;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.Parser;
import org.cp.confisual.nevisauth.NevisAuthVisualiser;
import org.cp.confisual.nevisauth.VisualisationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.cp.confisual.nevisauth.NevisAuthVisualiser.IMG_EXTENSION;
import static org.junit.jupiter.api.Assertions.*;

class NevisAuthStateDiagramIntTest {

  // support objects
  private final Path destinationFolder = Paths.get(System.getProperty("user.dir") + "/build/libs");
  private final File nevisAuthFile = TestUtils.getTestResourceFile("esauth4.xml");
  private List<Domain> domains;

  @BeforeEach
  void setUp() throws ParserException {
    domains = new Parser().parse(nevisAuthFile);
    domains.forEach(domain -> {
      try {
        Files.deleteIfExists(destinationFolder.resolve(domain.getName() + IMG_EXTENSION));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Test
  void canGenerateNevisAuthStateDiagram() throws VisualisationException {
    // given
    NevisAuthVisualiser visualiser = new NevisAuthVisualiser(destinationFolder);

    // when
    visualiser.visualiseDomains(nevisAuthFile);

    // then
    domains.forEach(domain -> assertTrue(Files.exists(destinationFolder.resolve(domain.getName() + IMG_EXTENSION))));
  }
}

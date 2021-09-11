package org.cp.confisual.nevisauth.integration;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.cp.confisual.TestUtils;
import org.cp.confisual.nevisauth.NevisAuthVisualiser;
import org.cp.confisual.VisualisationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NevisAuthStateDiagramIntTest {

  NevisAuthVisualiser underTest;

  @BeforeEach
  public void setUp() {
    underTest = new NevisAuthVisualiser();
  }

  @Test
  void shouldGenerateNevisAuthStateDiagramWithConfigFile() throws VisualisationException {
    // given
    File nevisAuthFile = TestUtils.getTestResourceFile("esauth4.xml");

    // when
    Map<String, String> encodedImages = underTest.visualiseDomains(nevisAuthFile);

    // then
    assertTrue(encodedImages.size() > 0);
  }

  @Test
  void shouldGenerateNevisAuthStateDiagramWithConfigString() throws VisualisationException, IOException {
    // given
    String nevisAuthString = TestUtils.getTestResourceContent("esauth4.xml");

    // when
    Map<String, String> encodedImages = underTest.visualiseDomains(nevisAuthString);

    // then
    assertTrue(encodedImages.size() > 0);
  }
}

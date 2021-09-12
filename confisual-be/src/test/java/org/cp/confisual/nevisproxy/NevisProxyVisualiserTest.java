package org.cp.confisual.nevisproxy;

import org.cp.confisual.TestUtils;
import org.cp.confisual.VisualisationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NevisProxyVisualiserTest {

  private NevisProxyVisualiser underTest;

  @BeforeEach
  void setUp() {
    underTest = new NevisProxyVisualiser();
  }

  @Test
  void shouldGenerateNevisProxyUrlPattern_givenWebXmlFile() throws VisualisationException {
    // given
    File webXmlFile = TestUtils.getTestResourceFile("web.xml");

    // when
    Map<String, String> encodedImages = underTest.visualiseUrlPattern(webXmlFile);

    // then
    assertTrue(encodedImages.size() > 0);
  }

  @Test
  void shouldGenerateNevisProxyUrlPattern_givenWebXmlContent() throws VisualisationException, IOException {
    // given
    String webXmlContent = TestUtils.getTestResourceContent("web.xml");

    // when
    Map<String, String> encodedImages = underTest.visualiseUrlPattern(webXmlContent);

    // then
    assertTrue(encodedImages.size() > 0);
  }
}

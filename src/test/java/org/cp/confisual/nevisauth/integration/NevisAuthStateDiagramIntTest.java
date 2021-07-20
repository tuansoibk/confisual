package org.cp.confisual.nevisauth.integration;

import java.util.List;

import org.cp.confisual.ParserException;
import org.cp.confisual.TestUtils;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.Parser;
import org.cp.confisual.nevisauth.Visualiser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class NevisAuthStateDiagramIntTest {

  private Visualiser underTest;

  @BeforeEach
  void setUp() {
    underTest = new Visualiser();
  }

  @Test
  void canGenerateNevisAuthStateDiagram() throws ParserException {
    // given
    List<Domain> domains = new Parser().parse(TestUtils.getTestResourceFile("esauth4.xml"));

    // when
    underTest.visualise(domains);

    // then
    // assert visualisation result exists
    fail("not yet implemented");
  }
}

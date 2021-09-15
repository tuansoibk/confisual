package org.cp.confisual.nevisauth;

import org.cp.confisual.ParserException;
import org.cp.confisual.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParserTest {

  @Test
  void shouldParseEsAuthConfigToDomainList_givenConfigFile() throws ParserException, IOException {
    // when
    List<Domain> actual = Parser.parse(TestUtils.getTestResourceFile("esauth4.xml"));

    // then
    assertDomains(actual);
  }

  @Test
  void shouldParseEsAuthConfigToDomainList_givenConfigString() throws ParserException, IOException {
    // when
    List<Domain> actual = Parser.parse(TestUtils.getTestResourceContent("esauth4.xml"));

    // then
    assertDomains(actual);
  }

  private void assertDomains(List<Domain> actual) {
    Domain domain = actual.get(0);
    assertEquals("TWOFASSO", domain.getName());
    assertEquals(5, domain.getEntries().size());
    assertEquals(15, domain.getAuthStates().size());
    assertFalse(domain.getAuthStates().containsKey(AuthState.NOT_EXISTING.getName()));
  }
}

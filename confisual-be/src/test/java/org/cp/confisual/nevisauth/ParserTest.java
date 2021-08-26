package org.cp.confisual.nevisauth;

import org.cp.confisual.ParserException;
import org.cp.confisual.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ParserTest {

  private Parser underTest;

  @BeforeEach
  void setUp() {
    underTest = new Parser();
  }

  @Test
  void canParseEsAuthConfigToDomainArray() throws ParserException {
    // when
    List<Domain> actual = underTest.parse(TestUtils.getTestResourceFile("esauth4.xml"));

    // then
    Domain domain = actual.get(0);
    assertEquals("TWOFASSO", domain.getName());
    assertEquals(5, domain.getEntries().size());
    assertEquals(15, domain.getAuthStates().size());
    assertFalse(domain.getAuthStates().containsKey(AuthState.NOT_EXISTING.getName()));
  }
}

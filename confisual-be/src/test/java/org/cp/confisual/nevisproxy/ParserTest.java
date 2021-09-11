package org.cp.confisual.nevisproxy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.cp.confisual.ParserException;
import org.cp.confisual.TestUtils;
import org.junit.jupiter.api.Test;

class ParserTest {

  @Test
  void shouldParseWebXmlConfigToUrlPatternList_givenConfigFile() throws ParserException {
    // when
    List<UrlPattern> actual = Parser.parse(TestUtils.getTestResourceFile("web.xml"));

    // then
    assertUrlPatterns(actual);
  }

  @Test
  void shouldParseWebXmlConfigToUrlPatternList_givenConfigString() throws ParserException, IOException {
    // when
    List<UrlPattern> actual = Parser.parse(TestUtils.getTestResourceContent("web.xml"));

    // then
    assertUrlPatterns(actual);
  }

  private void assertUrlPatterns(List<UrlPattern> actual) {
    assertEquals(6, actual.size());

    UrlPattern urlPattern = actual.get(0);
    assertEquals("/*", urlPattern.getPattern());
    assertEquals(1, urlPattern.getFilters().size());
    assertEquals("CookieBasedSessionManagementFilter", urlPattern.getFilters().get(0).getName());
    assertEquals("NevisDefaultServlet", urlPattern.getServlet().getName());

    urlPattern = actual.get(1);
    assertEquals("/sso/*", urlPattern.getPattern());
    assertEquals(3, urlPattern.getFilters().size());
    assertEquals("CookieBasedSessionManagementFilter", urlPattern.getFilters().get(0).getName());
    assertEquals("AuthenticationFilter2FA", urlPattern.getFilters().get(1).getName());
    assertEquals("SecTokenBasicAuthDelegation", urlPattern.getFilters().get(2).getName());
    assertEquals("Servlet2FA", urlPattern.getServlet().getName());

    urlPattern = actual.get(2);
    assertEquals("/2fa/mailtan/*", urlPattern.getPattern());
    assertEquals(4, urlPattern.getFilters().size());
    assertEquals("CookieBasedSessionManagementFilter", urlPattern.getFilters().get(0).getName());
    assertEquals("AuthenticationFilter2FA", urlPattern.getFilters().get(1).getName());
    assertEquals("TwoFARoleFilter", urlPattern.getFilters().get(2).getName());
    assertEquals("SecTokenBasicAuthDelegation", urlPattern.getFilters().get(3).getName());
    assertEquals("Servlet2FA", urlPattern.getServlet().getName());

    urlPattern = actual.get(3);
    assertEquals("/2fa/oath/*", urlPattern.getPattern());
    assertEquals(4, urlPattern.getFilters().size());
    assertEquals("CookieBasedSessionManagementFilter", urlPattern.getFilters().get(0).getName());
    assertEquals("AuthenticationFilter2FA", urlPattern.getFilters().get(1).getName());
    assertEquals("TwoFARoleFilter", urlPattern.getFilters().get(2).getName());
    assertEquals("SecTokenBasicAuthDelegation", urlPattern.getFilters().get(3).getName());
    assertEquals("Servlet2FA", urlPattern.getServlet().getName());

    urlPattern = actual.get(4);
    assertEquals("/protected/mailtan/*", urlPattern.getPattern());
    assertEquals(5, urlPattern.getFilters().size());
    assertEquals("CookieBasedSessionManagementFilter", urlPattern.getFilters().get(0).getName());
    assertEquals("AuthenticationFilter2FA", urlPattern.getFilters().get(1).getName());
    assertEquals("TwoFARoleFilter", urlPattern.getFilters().get(2).getName());
    assertEquals("AdminRoleFilter", urlPattern.getFilters().get(3).getName());
    assertEquals("SecTokenBasicAuthDelegation", urlPattern.getFilters().get(4).getName());
    assertEquals("Servlet2FA", urlPattern.getServlet().getName());

    urlPattern = actual.get(5);
    assertEquals("/protected/oath/*", urlPattern.getPattern());
    assertEquals(5, urlPattern.getFilters().size());
    assertEquals("CookieBasedSessionManagementFilter", urlPattern.getFilters().get(0).getName());
    assertEquals("AuthenticationFilter2FA", urlPattern.getFilters().get(1).getName());
    assertEquals("TwoFARoleFilter", urlPattern.getFilters().get(2).getName());
    assertEquals("AdminRoleFilter", urlPattern.getFilters().get(3).getName());
    assertEquals("SecTokenBasicAuthDelegation", urlPattern.getFilters().get(4).getName());
    assertEquals("Servlet2FA", urlPattern.getServlet().getName());
  }
}

package org.cp.confisual.nevisproxy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterMappingTest {

  @ParameterizedTest
  @CsvSource({
      "/*, /oidc/*, true",
      "/oidc/*, /*, false",
      "/oidc/*, /oidc/sp/*, true",
      "/oidc/*, /oidc, true",
      "/oidc/*, /oidc/, true",
      "/oidc/*, /oidca, false",
      "/oidc/*, /oid, false",
      "/oidc/*, /oid/, false",
      "/oidc/*, /oidca/aslkdf, false",
      "/oidc*, /oidc, false",
      "/oidc*, /oidc*, false",
      "/oidc*, /oidc/, false",
      "/oidc/, /oidc, false",
      "/oidc*, /oidcabc, false",
      "/oidc, /oidc/, false",
      "/oidc, /oidc, true"
  })
  void includes(String parentPattern, String childPattern, boolean expectedResult) {
    // given
    FilterMapping filterMapping = new FilterMapping(null, parentPattern);

    // then
    assertEquals(expectedResult, filterMapping.includePattern(childPattern));
  }
}

/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServletMapping {

  private final String servletName;
  private final String urlPattern;

  public int calculateMatchingLength(String pattern) {
    int minLen = Math.min(urlPattern.length(), pattern.length());
    for (int i = 0; i < minLen; i++) {
      if (urlPattern.charAt(i) != pattern.charAt(i)) {
        return i;
      }
    }

    return minLen;
  }
}

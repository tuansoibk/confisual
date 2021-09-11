/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FilterMapping {

  private final String filterName;
  private final String urlPattern;

  /**
   * Returns true if this mapping's URL pattern includes given child URL pattern, otherwise false.
   * <p>
   * A child pattern is included when all of its accepted URLs are accepted by this mapping's URL pattern.
   * <p>
   * Example: given this mapping's URL pattern = '/oidc/*' and child pattern = '/oidc/sp/*' --> result = true
   * <p>
   * It is because bellow URLs, accepted by the child pattern, are all accepted by this mapping's URL pattern:
   * <ul>
   *   <li>/oidc/sp</li>
   *   <li>/oidc/sp/</li>
   *   <li>/oidc/sp/xyz</li>
   * </ul>
   */
  public boolean includePattern(String childPattern) {
    String matchAllPattern = "/*";
    int pos = urlPattern.indexOf(matchAllPattern);

    if (pos != -1) {
      for (int i = 0; i < pos; i++) {
        if (!hasCharAtIndex(childPattern, urlPattern.charAt(i), i)) {
          return false;
        }
      }

      return noCharOrHasSlashAtIndex(childPattern, pos);
    }

    return urlPattern.equals(childPattern) && lastCharNotStar(urlPattern) && lastCharNotStar(childPattern);
  }

  private static boolean noCharOrHasSlashAtIndex(String pattern, int index) {
    return pattern.length() <= index || pattern.charAt(index) == '/';
  }

  private static boolean lastCharNotStar(String pattern) {
    return pattern.charAt(pattern.length() - 1) != '*';
  }

  private static boolean hasCharAtIndex(String pattern, char ch, int index) {
    return pattern.length() > index && pattern.charAt(index) == ch;
  }
}

/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Servlet {

  private final String name;
  private final String clazz;
}

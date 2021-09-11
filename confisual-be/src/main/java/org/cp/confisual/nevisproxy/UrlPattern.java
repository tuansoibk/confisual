/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisproxy;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class UrlPattern {

  private final String pattern;
  @Setter
  private List<Filter> filters;
  @Setter
  private Servlet servlet;
}

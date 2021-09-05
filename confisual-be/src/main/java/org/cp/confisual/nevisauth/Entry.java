package org.cp.confisual.nevisauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class Entry {

  private final String method;
  private final String authStateName;
  private final String selector;
  @Setter
  private AuthState authState;
}

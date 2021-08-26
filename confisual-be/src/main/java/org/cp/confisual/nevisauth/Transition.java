package org.cp.confisual.nevisauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class Transition {

  private final String name;
  private final String authStateName;
  @Setter
  private AuthState authState;
}

package org.cp.confisual.nevisauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class AuthState {

  public static final AuthState NOT_EXISTING = new AuthState("NotExisting", "org.cp.confisual.NotExisting");

  private final String name;
  private final String clazz;
  private final List<Transition> transitions = new ArrayList<>();

  public void addTransition(Transition transition) {
    transitions.add(transition);
  }
}

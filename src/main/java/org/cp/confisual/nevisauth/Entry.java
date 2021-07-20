package org.cp.confisual.nevisauth;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.cp.confisual.plantuml.NevisAuthObject;
import org.cp.confisual.plantuml.NevisAuthVisitor;

@RequiredArgsConstructor
@Getter
public class Entry implements NevisAuthObject {

  private final String method;
  private final String authStateName;
  private final String selector;
  @Setter
  private AuthState authState;
  public static List<String> existingAuthStates = new ArrayList<>();
  @Override
  public void accept(NevisAuthVisitor nevisAuthVisitor) {
    nevisAuthVisitor.visit(this);

    Stack<AuthState> authStateS = new Stack<>();
    authStateS.add(this.getAuthState());

    while (!authStateS.isEmpty()) {
      AuthState authState = authStateS.pop();

      if (existingAuthStates.contains(authState.getName())) {
        continue;
      }

      existingAuthStates.add(authState.getName());
      authState.accept(nevisAuthVisitor);
      authState.getTransitions().forEach(transition -> authStateS.add(transition.getAuthState()));
    }
  }
}

package org.cp.confisual.nevisauth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cp.confisual.VisitableConfigObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Domain implements VisitableConfigObject<NevisAuthConfigVisitor> {

  private final String name;
  private final Map<String, AuthState> authStates = new HashMap<>();
  private final List<Entry> entries = new ArrayList<>();

  public void addAuthState(AuthState authState) {
    this.authStates.put(authState.getName(), authState);
  }

  public void addEntry(Entry entry) {
    this.entries.add(entry);
  }

  @Override
  public void accept(NevisAuthConfigVisitor visitor) {
    visitor.visit(this);
    this.getEntries().forEach(entry -> entry.getAuthState().accept(visitor));
  }
}

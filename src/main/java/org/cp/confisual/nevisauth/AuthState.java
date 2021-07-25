package org.cp.confisual.nevisauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class AuthState implements VisitableConfigObject {

	public static final AuthState NOT_EXISTING = new AuthState("NotExisting", "org.cp.confisual.NotExisting");

	private final String name;
	private final String clazz;
	private final List<Transition> transitions = new ArrayList<>();

	public static List<String> existingAuthStates = new ArrayList<>();

	public void addTransition(Transition transition) {
		transitions.add(transition);
	}

	@Override
	public void accept(NevisAuthConfigVisitor nevisAuthConfigVisitor) {
		if (existingAuthStates.contains(this.getName())) {
			return;
		}

		existingAuthStates.add(this.getName());
		nevisAuthConfigVisitor.visit(this);
		this.getTransitions().forEach(transition -> transition.getAuthState().accept(nevisAuthConfigVisitor));
	}
}

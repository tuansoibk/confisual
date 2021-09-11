/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisauth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantUmlVisitor implements NevisAuthConfigVisitor {

	public static final String STARTUML = "@startuml\n";

	public static final String ENDUML = "@enduml\n";

	public static final String NEXT_ARROW = "-->";

	public static final String ENDL = System.lineSeparator();

	public static final String POINT = "[*]";

	private List<String> existingAuthStates = new ArrayList<>();

	private StringBuilder sourceBuilder = new StringBuilder();

	public String getSourceBuilder() {
		return STARTUML + this.sourceBuilder.toString() + ENDUML;
	}

	@Override
	public boolean visit(AuthState authState) {
		if (authState == null) {
			return false;
		}

		if (existingAuthStates.contains(authState.getName())) {
				return false;
		}

		existingAuthStates.add(authState.getName());

		if (authState.getTransitions().isEmpty()) {
			//final auth state
			this.sourceBuilder.append(authState.getName() + NEXT_ARROW + POINT + ENDL);
			return false;
		}

		this.sourceBuilder.append(authState.getTransitions()
					.stream()
					.map(transition -> authState.getName() + NEXT_ARROW + transition.getAuthStateName() + ": " + transition.getName())
					.collect(Collectors.joining(ENDL)));
		this.sourceBuilder.append(ENDL);

		return true;
	}

	@Override
	public void visit(Domain domain) {
		if (domain == null) {
			return;
		}

		domain.getEntries().forEach(entry -> {
			this.sourceBuilder.append(domain.getName() + NEXT_ARROW + entry.getMethod() + ENDL);
			this.sourceBuilder.append(entry.getMethod() + NEXT_ARROW + entry.getAuthStateName() + ": " + entry.getSelector() + ENDL);
		});
	}
}

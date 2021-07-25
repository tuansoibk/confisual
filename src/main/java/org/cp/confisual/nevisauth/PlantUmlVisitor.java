/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisauth;

import java.util.stream.Collectors;

public class PlantUmlVisitor implements NevisAuthConfigVisitor {

	public static final String STARTUML = "@startuml\n";

	public static final String ENDUML = "@enduml\n";

	public static final String MAX_WEIGHT_HEIGHT = "scale max 2000*6000\n";

	public static final String NEXT_ARROW = "-->";

	public static final String ENDL = System.lineSeparator();

	public static final String POINT = "[*]";

	private StringBuilder plantumlSource = new StringBuilder();

	public String getPlantumlSource() {
		return STARTUML + MAX_WEIGHT_HEIGHT + this.plantumlSource.toString() + ENDUML;
	}

	@Override
	public void visit(AuthState authState) {
		if (authState == null) {
			return;
		}

		if (authState.getTransitions().isEmpty()) {
			//final auth state
			this.plantumlSource.append(authState.getName() + NEXT_ARROW + POINT);
		} else {
			this.plantumlSource.append(authState.getTransitions()
					.stream()
					.map(transition -> authState.getName() + NEXT_ARROW + transition.getAuthStateName() + ": " + transition.getName())
					.collect(Collectors.joining(ENDL)));
		}

		this.plantumlSource.append(ENDL);
	}

	@Override
	public void visit(Domain domain) {
		if (domain == null) {
			return;
		}

		domain.getEntries().forEach(entry -> {
			this.plantumlSource.append(domain.getName() + NEXT_ARROW + entry.getMethod() + ENDL);
			this.plantumlSource.append(entry.getMethod() + NEXT_ARROW + entry.getAuthStateName() + ": " + entry.getSelector() + ENDL);
		});
	}
}

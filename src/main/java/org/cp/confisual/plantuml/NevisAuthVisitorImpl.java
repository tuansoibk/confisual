/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.plantuml;

import java.util.stream.Collectors;

import org.cp.confisual.nevisauth.AuthState;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.Entry;

public class NevisAuthVisitorImpl implements NevisAuthVisitor {

	public static final String STARTUML = "@startuml\n";

	public static final String ENDUML = "@enduml\n";

	public static final String MAX_WEIGHT_HEIGHT = "scale max 2000*6000\n";

	public static final String NEXT_ARROW = "-->";

	public static final String ENDL = "\n";

	public static final String POINT = "[*]";

	private StringBuilder info = new StringBuilder();

	public String getInfo() {
		return STARTUML + MAX_WEIGHT_HEIGHT + this.info.toString() + ENDUML;
	}

	@Override
	public void visit(AuthState authState) {
		if (authState == null) {
			return;
		}

		if (authState.getTransitions().isEmpty()) {
			//final auth state
			this.info.append(authState.getName() + NEXT_ARROW + POINT);
		} else {
			this.info.append(authState.getTransitions()
					.stream()
					.map(transition -> authState.getName() + NEXT_ARROW + transition.getAuthStateName() + ": " + transition.getName())
					.collect(Collectors.joining(ENDL)));
		}

		this.info.append(ENDL);
	}

	@Override
	public void visit(Entry entry) {
		if (entry == null) {
			return;
		}

		this.info.append(entry.getMethod() + NEXT_ARROW + entry.getAuthStateName() + ": " + entry.getSelector() + ENDL);
	}

	@Override
	public void visit(Domain domain) {
		if (domain == null) {
			return;
		}

		this.info.append(domain.getEntries()
				.stream()
				.map(entry -> domain.getName() + NEXT_ARROW + entry.getMethod() + ENDL)
				.collect(Collectors.joining("")));
	}
}

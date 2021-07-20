/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.plantuml;

import org.cp.confisual.nevisauth.AuthState;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.Entry;

public interface NevisAuthVisitor {
	void visit(AuthState authState);
	void visit(Entry entry);
	void visit(Domain domain);

	String getInfo();
}

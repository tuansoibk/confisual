/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisauth;

public interface NevisAuthConfigVisitor {
	void visit(AuthState authState);
	void visit(Domain domain);
}

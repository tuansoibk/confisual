/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisauth;

public interface NevisAuthConfigVisitor {
	boolean visit(AuthState authState);
	void visit(Domain domain);
	String getSourceBuilder();
}

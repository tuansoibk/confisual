/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual.nevisauth;

public interface VisitableConfigObject {
	void accept(NevisAuthConfigVisitor nevisAuthConfigVisitor);
}

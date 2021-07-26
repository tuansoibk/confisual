/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual;

import org.cp.confisual.nevisauth.NevisAuthConfigVisitor;

public interface VisitableConfigObject<T> {
	void accept(T object);
}

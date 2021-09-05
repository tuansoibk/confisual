/*
 * Author : AdNovum Informatik AG
 */

package org.cp.confisual;

public interface VisitableConfigObject<T> {
	void accept(T visitor);
}

package org.cp.confisual.plantuml;

import static org.junit.jupiter.api.Assertions.*;

import org.cp.confisual.nevisauth.AuthState;
import org.cp.confisual.nevisauth.Domain;
import org.cp.confisual.nevisauth.Entry;
import org.cp.confisual.nevisauth.PlantUmlVisitor;
import org.cp.confisual.nevisauth.Transition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlantUmlVisitorTest {

	PlantUmlVisitor underTest;

	// supporting objects
	private AuthState authState;
	private Entry entry;
	private Domain domain;

	@BeforeEach
	public void setUp() {
		underTest = new PlantUmlVisitor();
		setUpTestData();
		AuthState.existingAuthStates.clear();
	}

	private void setUpTestData() {
		this.domain = new Domain("TestDomain");
		this.entry = new Entry("authenticate", "TestState", "/Test");

		this.authState = new AuthState("TestState", "Test");
		Transition transition = new Transition("ok", "TestState");
		AuthState doneState = new AuthState("Done", "Done");
		transition.setAuthState(doneState);
		this.authState.addTransition(transition);

		entry.setAuthState(this.authState);
		this.domain.addEntry(this.entry);
	}

	@Test
	public void canVisitAuthState() {
		// then
		this.authState.accept(underTest);

		// when
		String plantUmlSource = underTest.getPlantumlSource();
		assertTrue(plantUmlSource.contains("TestState"));
		assertTrue(plantUmlSource.contains("ok"));
		assertTrue(plantUmlSource.contains("Done"));
	}

	@Test
	public void canVisitDomain() {
		// then
		this.domain.accept(underTest);

		// when
		String plantUmlSource = underTest.getPlantumlSource();
		assertTrue(plantUmlSource.contains("TestDomain"));
		assertTrue(plantUmlSource.contains("authenticate"));
		assertTrue(plantUmlSource.contains("/Test"));
		assertTrue(plantUmlSource.contains("TestState"));
		assertTrue(plantUmlSource.contains("ok"));
		assertTrue(plantUmlSource.contains("Done"));
	}
}

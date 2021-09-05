package org.cp.confisual.plantuml;

import static org.cp.confisual.nevisauth.PlantUmlVisitor.NEXT_ARROW;
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
	}

	private void setUpTestData() {
		this.domain = new Domain("TestDomain");
		this.entry = new Entry("authenticate", "TestState", "/test");

		this.authState = new AuthState("TestState", "Test");
		Transition transition = new Transition("ok", "Done");
		AuthState doneState = new AuthState("Done", "Done");
		transition.setAuthState(doneState);
		this.authState.addTransition(transition);

		entry.setAuthState(this.authState);
		this.domain.addEntry(this.entry);
	}

	@Test
	public void canVisitAuthState() {
		// when
		this.authState.accept(underTest);

		// then
		String plantUmlSource = underTest.getSourceBuilder();
		assertTrue(plantUmlSource.contains("TestState"));
		assertTrue(plantUmlSource.contains("ok"));
		assertTrue(plantUmlSource.contains("Done"));
	}

	@Test
	public void canVisitDomain() {
		// when
		this.domain.accept(underTest);

		// then
		String plantUmlSource = underTest.getSourceBuilder();
		assertTrue(plantUmlSource.contains("TestDomain"));
		assertTrue(plantUmlSource.contains("authenticate"));
		assertTrue(plantUmlSource.contains("/test"));
		assertTrue(plantUmlSource.contains("TestState"));
		assertTrue(plantUmlSource.contains("ok"));
		assertTrue(plantUmlSource.contains("Done"));
	}

	@Test
	public void canVisitStateUsedInMultipleEntries() {
			// given
			Entry secondEntry = new Entry("stepup", "TestState", "/stepup");
			secondEntry.setAuthState(this.authState);
			this.domain.addEntry(secondEntry);

			// when
			this.domain.accept(underTest);

			// then
			String plantUmlSource = underTest.getSourceBuilder();
			this.domain.getEntries().forEach(entry -> assertTrue(plantUmlSource.contains(entry.getMethod() + NEXT_ARROW + entry.getAuthStateName())));
	}

	@Test
	public void canVisitLoopAuthState() {
			// given
			Transition transition = new Transition("ok", "TestState");
			transition.setAuthState(this.authState);
			this.authState.addTransition(transition);

			// when
			this.domain.accept(underTest);

			// then
			String plantUmlSource = underTest.getSourceBuilder();
			assertTrue(plantUmlSource.contains(this.authState.getName() + NEXT_ARROW + this.authState.getName()));
	}
}

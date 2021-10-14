package ckrae.procy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PetriNetTest {

	private PetriNet net;

	private Place p1;
	private Place p2;
	private Place p3;

	private Transition t1;
	private Transition t2;

	@BeforeEach
	public void init() {

		net = new PetriNet();

		p1 = net.place("p1");
		p2 = net.place("p2");
		p3 = net.place("p3");

		t1 = net.transition("t1");
		t2 = net.transition("t2");

	}

	@Test
	@DisplayName("execute a petri net")
	void ExecutionTest() {

		net.arc(p1, t1);
		net.arc(t1, p2);
		net.arc(p2, t2);
		net.arc(t2, p3);

		Marking marking = new Marking(p1);
		assertTrue(net.canFire(marking));

		Marking result = net.execute(marking);
		assertNotNull(result);
		assertFalse(result.contains(p1));
		assertFalse(result.contains(p2));
		assertTrue(result.contains(p3));

		assertFalse(net.canFire(result));

	}

	@Test
	@DisplayName("execute petri net with loops")
	void ExecutionLoopsTest() {

		net.arc(p1, t1);
		net.arc(t1, p2);
		net.arc(p2, t2);
		net.arc(t2, p1);

		Marking marking = new Marking(p1);
		Marking res = net.execute(marking);

		assertNotNull(res);
		assertTrue(net.canFire(marking));

	}

	@Test
	@DisplayName("fire simple transition")
	void simpleTransitionFire() {

		net = new PetriNet();
		p1 = net.place("p1");
		p2 = net.place("p2");
		t1 = net.transition("t1");

		net.arc(p1, t1);
		net.arc(t1, p2);

		Marking marking = new Marking(p1);
		assertNotNull(net.getEnabledTransitions(marking));
		assertEquals(net.getEnabledTransitions(marking).size(), 1);

		Marking result = net.fire(marking);
		assertFalse(result.contains(p1));
		assertTrue(result.contains(p2));

	}

	@Test
	@DisplayName("fire transition with multiple incoming places")
	void multipleIncomingFire() {

		net = new PetriNet();
		p1 = net.place();
		p2 = net.place();
		p3 = net.place();
		t1 = net.transition();

		net.arc(p1, t1);
		net.arc(p2, t1);
		net.arc(t1, p3);

		Marking marking = new Marking(p1);
		assertNotNull(net.getEnabledTransitions(marking));
		assertEquals(net.getEnabledTransitions(marking).size(), 0);

		marking = new Marking(p1, p2);
		assertNotNull(net.getEnabledTransitions(marking));
		assertEquals(net.getEnabledTransitions(marking).size(), 1);

		Marking result = net.fire(marking);
		assertFalse(result.contains(p1));
		assertFalse(result.contains(p2));
		assertTrue(result.contains(p3));

	}

	@Test
	@DisplayName("fire transition with multiple outgoing places")
	void multipleOutgoingFire() {

		net = new PetriNet();
		p1 = net.place();
		p2 = net.place();
		p3 = net.place();
		t1 = net.transition();

		net.arc(p1, t1);
		net.arc(t1, p2);
		net.arc(t1, p3);

		Marking marking = new Marking();
		assertNotNull(net.getEnabledTransitions(marking));
		assertEquals(net.getEnabledTransitions(marking).size(), 0);

		marking = new Marking(p1);
		assertNotNull(net.getEnabledTransitions(marking));
		assertEquals(net.getEnabledTransitions(marking).size(), 1);

		Marking result = net.fire(marking);
		assertFalse(result.contains(p1));
		assertTrue(result.contains(p2));
		assertTrue(result.contains(p3));

	}

}

package ckrae.procy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetriNetTest {

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
	public void ExecutionTest() {

		net.arc(p1, t1);
		net.arc(t1, p2);
		net.arc(p2, t2);
		net.arc(t2, p3);

		Marking marking = new Marking(p1);
		assertTrue(net.canFire(marking));

		Marking res = net.execute(marking);
		assertNotNull(res);
		assertFalse(res.contains(p1));
		assertFalse(res.contains(p2));
		assertTrue(res.contains(p3));

		assertFalse(net.canFire(marking));

	}

	@Test
	public void ExecutionLoopsTest() {

		net.arc(p1, t1);
		net.arc(t1, p2);
		net.arc(p2, t2);
		net.arc(t2, p1);

		Marking marking = new Marking(p1);
		assertTrue(net.canFire(marking));

		Marking res = net.execute(marking);
		assertNotNull(res);
		assertTrue(net.canFire(marking));

	}

	@Test
	public void simpleTransitionFire() {

		net = new PetriNet();
		p1 = net.place("p1");
		p2 = net.place("p2");
		t1 = net.transition("t1");

		net.arc(p1, t1);
		net.arc(t1, p2);

		Marking marking = new Marking(p1);
		assertNotNull(net.getEnabledTransitions(marking));
		assertEquals(net.getEnabledTransitions(marking).size(), 1);

		net.fire(marking);
		assertFalse(marking.contains(p1));
		assertTrue(marking.contains(p2));

	}

	@Test
	public void multipleIncomingFire() {

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

		net.fire(marking);
		assertFalse(marking.contains(p1));
		assertFalse(marking.contains(p2));
		assertTrue(marking.contains(p3));

	}

	@Test
	public void multipleOutgoingFire() {

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

		net.fire(marking);
		assertFalse(marking.contains(p1));
		assertTrue(marking.contains(p2));
		assertTrue(marking.contains(p3));

	}

}

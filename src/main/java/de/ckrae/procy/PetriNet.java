package de.ckrae.procy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.Validate;

/**
 * This class represents a petri net.
 *
 */
public class PetriNet {

	/**
	 * The transitions of this petri net.
	 */
	private final List<Transition> transitions;

	/**
	 * The places of this petri net.
	 */
	private final List<Place> places;

	/**
	 * Create a empty petri net.
	 */
	public PetriNet() {
		this.transitions = new ArrayList<>();
		this.places = new ArrayList<>();
	}

	/**
	 * Add a transition to this petri net.
	 * 
	 * @return the new transition
	 */
	public Transition transition() {
		int size = this.transitions.size();
		String name = "t" + size;
		return transition(name);
	}

	/**
	 * Add a transition to this petri net.
	 * 
	 * @param name
	 * @return
	 */
	public Transition transition(String name) {
		Transition transition = new Transition(name);
		this.transitions.add(transition);
		return transition;
	}

	/**
	 * Add a place to this petri net.
	 * 
	 * @return the new place
	 */
	public Place place() {
		int size = this.places.size();
		String name = "p" + String.valueOf(size);
		return place(name);
	}

	/**
	 * Add a place to this petri net.
	 * 
	 * @param name
	 * @return
	 */
	public Place place(String name) {
		Place place = new Place(name);
		this.places.add(place);
		return place;
	}

	/**
	 * Add a arc to this petri net that connects a place and a transition
	 * 
	 * @param place
	 * @param transition
	 */
	public void arc(Place place, Transition transition) {

		Validate.notNull(place);
		Validate.notNull(transition);

		Validate.isTrue(this.places.contains(place), "place not part of petri net");
		Validate.isTrue(this.transitions.contains(transition), "transition not part of petri net");

		transition.addIncoming(place);

	}

	/**
	 * Add a arc to this petri net that connects a transition and a place.
	 * 
	 * @param place
	 * @param transition
	 */
	public void arc(Transition transition, Place place) {

		Validate.notNull(place);
		Validate.notNull(transition);

		Validate.isTrue(this.places.contains(place), "place not part of petri net");
		Validate.isTrue(this.transitions.contains(transition), "transition not part of petri net");

		transition.addOutgoing(place);

	}

	/**
	 * Get all transitions of this petri net that are able to fire with respect to
	 * the given marking.
	 * 
	 * @param marking
	 * @return list of transitions
	 */
	public List<Transition> getEnabledTransitions(Marking marking) {

		Validate.notNull(marking);

		List<Transition> res = new ArrayList<>();
		for (Transition transition : this.transitions) {
			if (transition.canFire(marking))
				res.add(transition);
		}

		return res;

	}

	/**
	 * Returns true if petri net has enabled transitions with respect to the given
	 * marking.
	 * 
	 * @param marking
	 * @return true if petri net can fire
	 */
	public boolean canFire(Marking marking) {

		Validate.notNull(marking);

		return !this.getEnabledTransitions(marking).isEmpty();

	}

	/**
	 * Perform a step of the petri net execution.
	 * 
	 * @param marking
	 * @return the resulting marking
	 */
	public Marking fire(Marking marking) {

		Validate.notNull(marking);

		List<Transition> enabledTransitions = this.getEnabledTransitions(marking);

		if (enabledTransitions.isEmpty())
			return marking;

		if (enabledTransitions.size() == 1)
			return marking.fireTransition(enabledTransitions.get(0));

		// fire random enabled transition
		Random random = new Random();
		Integer i = random.nextInt(enabledTransitions.size());
		Transition transition = enabledTransitions.get(i);

		marking.fireTransition(transition);

		return marking;
	}

	/**
	 * Execute firing steps until petri net can't fire anymore or the default
	 * maximum number of iterations has been reached
	 * 
	 * @param marking the initial marking
	 * @return
	 */
	public Marking execute(Marking marking) {
		return execute(marking, 1000);
	}

	/**
	 * Execute firing steps until petri net can't fire anymore or the maximum number
	 * of iterations has been reached
	 * 
	 * @param marking  the initial marking
	 * @param maxSteps number of max iterations
	 * @return the final marking
	 */
	public Marking execute(Marking marking, int maxSteps) {
		Validate.notNull(marking);

		int step = 0;
		while (this.canFire(marking) && step < maxSteps) {
			marking = this.fire(marking);
			step++;
		}
		return marking;
	}

}

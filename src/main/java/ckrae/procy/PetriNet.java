package ckrae.procy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a Petri net.
 *
 */
public class PetriNet {

	private List<Transition> transitions;
	private List<Place> places;

	public PetriNet() {
		this.transitions = new ArrayList<Transition>();
		this.places = new ArrayList<Place>();
	}

	/**
	 * Add a transition to this petri net
	 * 
	 * @return the new transition
	 */
	public Transition transition() {
		int size = this.transitions.size();
		String name = "t" + String.valueOf(size);
		return transition(name);
	}

	public Transition transition(String name) {
		Transition transition = new Transition(name);
		this.transitions.add(transition);
		return transition;
	}

	/**
	 * Add a place to this petri net
	 * 
	 * @return the new place
	 */
	public Place place() {
		int size = this.places.size();
		String name = "p" + String.valueOf(size);
		return place(name);
	}

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
		assert this.places.contains(place) : "place not part of petri net";
		assert this.transitions.contains(transition) : "transition not part of petri net";

		transition.addIncoming(place);

	}

	/**
	 * Add a arc to this petri net that connects a transition and a place
	 * 
	 * @param place
	 * @param transition
	 */
	public void arc(Transition transition, Place place) {
		assert this.places.contains(place) : "place not part of petri net";
		assert this.transitions.contains(transition) : "transition not part of petri net";

		transition.addOutgoing(place);

	}

	/**
	 * Get all transitions of this petri net that are able to fire with respect to
	 * the given marking
	 * 
	 * @param marking
	 * @return
	 */
	public List<Transition> getEnabledTransitions(Marking marking) {
		assert marking != null : "marking is null";

		List<Transition> res = new ArrayList<>();
		for (Transition transition : this.transitions) {
			if (transition.canFire(marking))
				res.add(transition);
		}
		return res;

	}

	/**
	 * Perform a step of the petri net execution
	 * 
	 * @param marking
	 * @return the resulting marking
	 */
	public Marking fire(Marking marking) {
		assert marking != null : "marking is null";

		List<Transition> transitions = this.getEnabledTransitions(marking);

		if (transitions.isEmpty())
			return marking;

		Random random = new Random();
		Integer i = random.nextInt(transitions.size());
		Transition transition = transitions.get(i);

		marking.fireTransition(transition);

		return marking;
	}

}

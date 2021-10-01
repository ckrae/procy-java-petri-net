package ckrae.procy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Marking class contains all places of a petri net that have a token.
 *
 */
public class Marking {

	/**
	 * List of places that contain a token
	 */
	public List<Place> places;

	public Marking() {
		this.places = new ArrayList<Place>();
	}

	public Marking(Place... places) {
		List<Place> list = new LinkedList<>();
		for (Place place : places)
			list.add(place);
		this.places = list;
	}

	public Marking(List<Place> list) {
		assert list != null;
		this.places = list;
	}

	/**
	 * @param place
	 * @return true if place is part of this marking
	 */
	public boolean contains(Place place) {
		assert place != null;

		return this.places.contains(place);
	}

	/**
	 * Fire a transition of a petri net. Modifies this marking to the resulting
	 * marking.
	 * 
	 * @param transition that fires
	 */
	protected void fireTransition(Transition transition) {

		assert transition.canFire(this) : "transition can not fire";

		List<Place> incoming = transition.getIncomingPlaces();
		for (Place place : incoming)
			this.places.remove(place);

		List<Place> outgoing = transition.getOutgoingPlaces();
		for (Place place : outgoing)
			this.places.add(place);

	}

}

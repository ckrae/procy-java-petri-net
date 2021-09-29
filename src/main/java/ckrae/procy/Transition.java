package ckrae.procy;

import java.util.ArrayList;
import java.util.List;

/**
 * Element of a {@link PetriNet}
 *
 */
public class Transition {

	private String name;
	private List<Place> incomingPlaces;
	private List<Place> outgoingPlaces;

	public Transition(String name) {
		this.name = name;
		this.incomingPlaces = new ArrayList<>();
		this.outgoingPlaces = new ArrayList<>();

	}

	/**
	 * Add a incoming place
	 * 
	 * @param place
	 */
	protected void addIncoming(Place place) {
		assert place != null : "place is null";

		this.incomingPlaces.add(place);
	}

	/**
	 * Add a outgoing place
	 * 
	 * @param place
	 */
	protected void addOutgoing(Place place) {
		assert place != null : "place is null";

		this.outgoingPlaces.add(place);
	}

	/**
	 * @return the incomingPlaces
	 */
	public List<Place> getIncomingPlaces() {
		return incomingPlaces;
	}

	/**
	 * @return the outgoingPlaces
	 */
	public List<Place> getOutgoingPlaces() {
		return outgoingPlaces;
	}

	/**
	 * @return true if this transition has incoming places
	 */
	public boolean hasIncoming() {
		assert this.incomingPlaces != null : "incoming places not initialized";

		return !this.incomingPlaces.isEmpty();
	}

	/**
	 * A Transition can fire if all incoming places are part of the marking.
	 * 
	 * @param marking
	 * @return true if this transition can fire
	 */
	public boolean canFire(Marking marking) {
		assert marking != null : "marking is null";

		if (!this.hasIncoming())
			return true;

		for (Place place : this.incomingPlaces)
			if (!marking.contains(place))
				return false;

		return true;

	}

}

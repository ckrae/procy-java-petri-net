package de.ckrae.procy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * Element of a {@link PetriNet}
 *
 */
public class Transition {

	/**
	 * Name of this transition.
	 */
	private final String name;

	/**
	 * Incoming places
	 */
	private final List<Place> incomingPlaces;

	/**
	 * Outgoing places
	 */
	private final List<Place> outgoingPlaces;

	/**
	 * Create a new transition.
	 * 
	 * @param name
	 */
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
		Validate.notNull(place, "place is null");

		this.incomingPlaces.add(place);
	}

	/**
	 * Add a outgoing place
	 * 
	 * @param place
	 */
	protected void addOutgoing(Place place) {
		Validate.notNull(place, "place is null");

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
		Validate.notNull(incomingPlaces, "incoming places not initialized");

		return !this.incomingPlaces.isEmpty();
	}

	/**
	 * A Transition can fire if all incoming places are part of the marking.
	 * 
	 * @param marking
	 * @return true if this transition can fire
	 */
	public boolean canFire(Marking marking) {
		Validate.notNull(marking, "marking is null");

		if (!this.hasIncoming())
			return true;

		for (Place place : this.incomingPlaces) {
			if (!marking.contains(place))
				return false;
		}

		return true;

	}

}

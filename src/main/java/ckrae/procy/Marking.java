package ckrae.procy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * The Marking class contains all places of a petri net that have a token.
 *
 */
public class Marking {

	/**
	 * List of places that contain a token
	 */
	private final List<Place> places;

	/**
	 * Create empty marking.
	 */
	public Marking() {
		this.places = new ArrayList<>();
	}

	/**
	 * Create marking with given places.
	 * 
	 * @param places
	 */
	public Marking(Place... places) {
		this(Arrays.asList(places));
	}

	/**
	 * Create marking with given places.
	 * 
	 * @param list
	 */
	public Marking(List<Place> places) {
		Validate.notNull(places);

		this.places = places;
	}

	/**
	 * Return true if this marking contains the given place.
	 * 
	 * @param place
	 * @return true if place is part of this marking
	 */
	public boolean contains(Place place) {
		Validate.notNull(place);

		return this.places.contains(place);
	}

	/**
	 * Fire a transition of a petri net. Return the resulting marking as a new
	 * marking.
	 * 
	 * @param transition that fires
	 */
	protected Marking fireTransition(Transition transition) {

		Validate.notNull(transition, "transition is null");
		Validate.isTrue(transition.canFire(this), "transition can not fire");

		List<Place> placeList = new ArrayList<>(this.places);
		for (Place place : transition.getIncomingPlaces())
			placeList.remove(place);

		for (Place place : transition.getOutgoingPlaces())
			placeList.add(place);

		return new Marking(placeList);

	}

}

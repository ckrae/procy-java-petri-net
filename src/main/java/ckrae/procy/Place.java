package ckrae.procy;

/**
 * Element of a {@link PetriNet}
 *
 */
public class Place {

	/**
	 * The name of this place.
	 */
	private final String name;

	public Place(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}

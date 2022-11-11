package com.appian.domain.enums;

/**
 * The Enum SuitCard.
 */
public enum SuitCard {
	
	/** The clubs. */
	CLUBS("Clubs"), 
	
	/** The diamonds. */
	DIAMONDS("Diamonds"), 
	
	/** The hearts. */
	HEARTS("Hearts"), 
	
	/** The spades. */
	SPADES("Spades");

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new suit card.
	 *
	 * @param name the name
	 */
	SuitCard(String name) {
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

}

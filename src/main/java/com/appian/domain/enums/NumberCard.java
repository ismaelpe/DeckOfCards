
package com.appian.domain.enums;

/**
 * The Enum NumberCard.
 */
public enum NumberCard {
	
	/** The one. */
	ONE ("1"),
	
	/** The two. */
	TWO ("2"),
	
	/** The three. */
	THREE ("3"),
	
	/** The four. */
	FOUR ("4"),
	
	/** The five. */
	FIVE ("5"),
	
	/** The six. */
	SIX ("6"),
	
	/** The seven. */
	SEVEN ("7"),
	
	/** The eight. */
	EIGHT ("8"),
	
	/** The nine. */
	NINE ("9"),
	
	/** The ten. */
	TEN ("10"),
	
	/** The jack. */
	JACK ("J"),
	
	/** The queen. */
	QUEEN ("Q"),
	
	/** The king. */
	KING ("K");
	
	/** The number. */
	private final String number;

	/**
	 * Instantiates a new number card.
	 *
	 * @param number the number
	 */
	NumberCard(String number) {
		this.number = number;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getNumber() {
		return this.number;
	}

}

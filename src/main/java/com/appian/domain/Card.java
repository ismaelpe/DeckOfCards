package com.appian.domain;

import java.util.Objects;

import com.appian.domain.enums.NumberCard;
import com.appian.domain.enums.SuitCard;

/**
 * The Class Card 
 */
public class Card {

	/** The number card. */
	private final NumberCard numberCard;

	/** The suit card. */
	private final SuitCard suitCard;

	/**
	 * Instantiates a new card.
	 *
	 * @param suitCard   the suit card
	 * @param numberCard the number card
	 */
	public Card(SuitCard suitCard, NumberCard numberCard) {
		super();
		this.suitCard = suitCard;
		this.numberCard = numberCard;
	}

	/**
	 * Gets the number card.
	 *
	 * @return the number card
	 */
	public NumberCard getNumberCard() {
		return numberCard;
	}

	/**
	 * Gets the suit card.
	 *
	 * @return the suit card
	 */
	public SuitCard getSuitCard() {
		return suitCard;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(numberCard, suitCard);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Card other = (Card) obj;
		
		return numberCard == other.numberCard && suitCard == other.suitCard;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Card [" + numberCard.getNumber() + " of " + suitCard.getName() + "]";
	}

}

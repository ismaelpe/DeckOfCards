package com.appian.service.impl;

import java.util.Collections;

import com.appian.domain.Card;
import com.appian.service.Dealer;

/**
 * The Class Dealer.
 */
public class DealerProvidedLiblmpl extends Dealer {

	/**
	 * Implement Shuffle.
	 */
	@Override
	protected void doShuffle() {
		// Java library-provided
		Collections.shuffle(super.deckOfCards);
	}

	/**
	 * Implement Deal one card.
	 *
	 * @return the optional
	 */
	@Override
	protected Card doDealOneCard() {
		return this.deckOfCards.remove(super.deckOfCards.size() - 1);
	}
}
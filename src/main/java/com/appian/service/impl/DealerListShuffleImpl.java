package com.appian.service.impl;

import java.util.ArrayList;
import java.util.Random;

import com.appian.domain.Card;
import com.appian.service.Dealer;

/**
 * The Class Dealer.
 */
public class DealerListShuffleImpl extends Dealer {

	/**
	 * Implement Shuffle.
	 */
	@Override
	protected void doShuffle() {
		var rnd = new Random();
		var cloned = new ArrayList<>(super.deckOfCards);
		super.deckOfCards.clear();
		
		// This algorithm is quite similar to the others with the difference 
		// that it operates with a buffer with which cards are randomly eliminated. 
		// These cards are kept in the order that they have been removed.
		while (!cloned.isEmpty()) {
			var idx_random = rnd.nextInt(cloned.size());
			super.deckOfCards.add(cloned.remove(idx_random));
		}
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
package com.appian.service.impl;

import java.util.Random;

import com.appian.domain.Card;
import com.appian.service.Dealer;

/**
 * The Class Dealer.
 */
public class DealerImpl extends Dealer {
	
	/**
	 * Implement Shuffle.
	 */
	@Override
	protected void doShuffle() {
		var rnd = new Random();
		var size = super.deckOfCards.size() - 1; // Calculate -1 to start for in a correct index

		// This algorithm iterates inversely to decrease the range of 
		// random numbers to be shuffled.
		for (var i = size; i > 0; i--) {
			// Get index between 0 (inclusive) to i (exclusive)
			var idx_random = rnd.nextInt(i);
			
			// Get the card in i) position
			var tmp = super.deckOfCards.get(i);
			
			// Set at position i) the card obtained randomly
			super.deckOfCards.set(i, super.deckOfCards.get(idx_random));
			
			// Set the original card in the random position
			super.deckOfCards.set(idx_random, tmp);
		}	
	}

	/**
	 * Implement Deal one card.
	 *
	 * @return the optional
	 */
	@Override
	protected Card doDealOneCard() {
		// Is more efficient reduce the list at the end
		return this.deckOfCards.remove(super.deckOfCards.size() - 1);
	}
}
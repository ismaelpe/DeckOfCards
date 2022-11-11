package com.appian.service.impl;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.appian.domain.Card;
import com.appian.service.Dealer;

/**
 * The Class Dealer.
 */
public class DealerArrayShuffleImpl extends Dealer {

	/**
	 * Implement Shuffle.
	 */
	@Override
	protected void doShuffle() {
		var rnd = new Random();
		var size = super.deckOfCards.size() - 1;

		final Object array[] = super.deckOfCards.toArray();
		
		// This algorithm is similar to DealerImpl but uses arrays instead of lists.
		for (var i = size; i > 0; i--) {
			var idx_random = rnd.nextInt(i);

			Object tmp = array[i];
			array[i] = array[idx_random];
			array[idx_random] = tmp;
		}
		
		super.deckOfCards.clear();
		super.deckOfCards.addAll(Stream.of(array).map(item -> (Card) item).collect(Collectors.toList()));
		
	}

	/**
	 * Implement Deal one card.
	 *
	 * @return the optional
	 */
	@Override
	protected Card doDealOneCard() {
		return this.deckOfCards.remove(0);
	}
}
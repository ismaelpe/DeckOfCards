package com.appian.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.appian.domain.Card;
import com.appian.domain.enums.NumberCard;
import com.appian.domain.enums.SuitCard;
import com.appian.service.exception.DealerOperationNotPermittedException;

/**
 * The Abstract Dealer.
 * 
 * This class contains the dealer business logic.
 */
public abstract class Dealer {
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(Dealer.class);

	/** The Constant ShuffleRepetitions. */
	private static final int ShuffleRepetitionsDefault = 10;

	/** The deck of cards. */
	protected final List<Card> deckOfCards;

	/** The dealer current status. */
	private DealerStatus currentStatus;

	/** The shuffle num. repetitions. */
	private int shuffleRepetitions;

	/**
	 * Instantiates a new dealer.
	 */
	protected Dealer() {
		this(ShuffleRepetitionsDefault);
	}

	/**
	 * Instantiates a new dealer.
	 *
	 * @param shuffleRepetitions the shuffle repetitions
	 */
	protected Dealer(int shuffleRepetitions) {
		this.deckOfCards = new ArrayList<>(); // ArrayList for rapid index access
		this.shuffleRepetitions = shuffleRepetitions;
	}

	/**
	 * Clean/Open deck of cards.
	 */
	public final void cleanDeckOfCards() {
		// Clean de list w
		this.deckOfCards.clear();
		// This lambda get a ordered Deck of Cards
		this.deckOfCards.addAll(Stream.of(SuitCard.values())
				.map(suiteCard -> Stream.of(NumberCard.values())
					.map(numberCard -> new Card(suiteCard, numberCard))
					.collect(Collectors.toList()))
				.collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll));

		this.currentStatus = DealerStatus.NONE;
	}

	/**
	 * Shuffle a inner deck of cards
	 */
	public final void shuffle() {	
		// We can't shuffle while dealer is dealing cards.
		if (this.currentStatus == DealerStatus.DEALING) {
			throw new DealerOperationNotPermittedException("Shuffle not permit during Dealing!!");
		}

		// If DEALING flag is down and no more card to dealt, we create new deck of cards
		if (this.deckOfCards.isEmpty()) {
			this.cleanDeckOfCards();
		}

		// We raise SHUFFLING flag to avoid deal cards
		this.currentStatus = DealerStatus.SHUFFLING;
		
		logger.debug("Current Status: "+ this.currentStatus);
		
		// Shuffle n) times to disperse the cards
		for (var counter = 0; counter < this.shuffleRepetitions; counter++) {
			this.doShuffle();
		}
		
		this.currentStatus = DealerStatus.NONE;
		
		logger.debug("Current Status: " + this.currentStatus);
	}

	/**
	 * Deal one card per request
	 *
	 * @return the optional
	 */
	public final Optional<Card> dealOneCard() {
		// We can't deal cards while dealer is shuffinfg cards.
		if (this.currentStatus == DealerStatus.SHUFFLING) {
			throw new DealerOperationNotPermittedException("Dealing not permit during shuffling!!");
		}

		if (!this.deckOfCards.isEmpty()) {
			// If exists cards to dealt, we return a card
			this.currentStatus = DealerStatus.DEALING; // Raise Dealing flag to avoid Shuffle
			return Optional.of(this.doDealOneCard());
		} else {
			// If all cards were dealt, we return a Empty Optional
			this.currentStatus = DealerStatus.NONE;
			return Optional.empty(); // No card
		}
		
	}

	/**
	 * Deal all card per request.
	 * 
	 * This method call n) times to dealOneCard
	 *
	 * @return the list
	 */
	public List<Card> dealAllCards() {
		// List with the all dealedCards
		var dealtCards = new ArrayList<Card>();
		
		var cardOptional = this.dealOneCard();
		
		while (cardOptional.isPresent()) { // if optional has value
			// Add de card
			dealtCards.add(cardOptional.get());
			// Get next card
			cardOptional = this.dealOneCard();
		}

		return dealtCards;
	}

	/**
	 * Do shuffle.
	 */
	protected abstract void doShuffle();

	/**
	 * Do deal one card.
	 *
	 * @return the card
	 */
	protected abstract Card doDealOneCard();

	/**
	 * Gets the shuffle repetitions.
	 *
	 * @return the shuffle repetitions
	 */
	public int getShuffleRepetitions() {
		return shuffleRepetitions;
	}

	/**
	 * Sets the shuffle repetitions.
	 *
	 * @param shuffleRepetitions the new shuffle repetitions
	 */
	public void setShuffleRepetitions(int shuffleRepetitions) {
		this.shuffleRepetitions = shuffleRepetitions;
	}

	/**
	 * The DealerStatus to control inner status.
	 */
	private enum DealerStatus {

		/** The none. */
		NONE,

		/** The shuffling. */
		SHUFFLING,

		/** The dealing. */
		DEALING
	}

}
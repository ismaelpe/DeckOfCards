package com.appian.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.appian.domain.Card;
import com.appian.domain.enums.NumberCard;
import com.appian.domain.enums.SuitCard;
import com.appian.service.Dealer;
import com.appian.service.exception.DealerOperationNotPermittedException;

/**
 * The Class DealerTest.
 */
public class DealerImplTest {

	/** The logger. */
	private static Logger logger = Logger.getLogger(DealerImplTest.class);

	/** The num cards. */
	private int numCards = SuitCard.values().length * NumberCard.values().length;

	/**
	 * Check that all cards were dealt and no return anymore.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_all_cards_were_dealt_and_no_return_anymore(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		dealer.cleanDeckOfCards();
		dealer.shuffle();

		var dequedCard = new ArrayList<Card>();
		var cardOptional = dealer.dealOneCard();

		while (cardOptional.isPresent()) {
			dequedCard.add(cardOptional.get());
			cardOptional = dealer.dealOneCard();
		}

		assertTrue(dequedCard.size() == this.numCards);
	}

	/**
	 * Check that no more cards are dealt when they were all dealt.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_no_more_cards_are_dealt_when_they_were_all_dealt(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");

		dealer.cleanDeckOfCards();

		dealer.shuffle();

		var dequedCard = new ArrayList<Card>();
		var cardOptional = dealer.dealOneCard();

		while (cardOptional.isPresent()) {
			dequedCard.add(cardOptional.get());
			cardOptional = dealer.dealOneCard();
		}

		for (int i = 0; i < 10; i++) {
			assertTrue(dealer.dealOneCard().isEmpty());
		}
	}

	/**
	 * Check that shuffling many times maintains the number of cards.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_shuffling_many_times_maintains_the_number_of_cards(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");

		dealer.cleanDeckOfCards();

		for (int i = 0; i < 10; i++) {
			dealer.shuffle();
		}

		var dequedCard = new ArrayList<Card>();
		var cardOptional = dealer.dealOneCard();

		while (cardOptional.isPresent()) {
			dequedCard.add(cardOptional.get());
			cardOptional = dealer.dealOneCard();
		}

		assertEquals(this.numCards, dequedCard.size());
	}

	/**
	 * Check open deck cards get same result.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_open_deck_cards_get_same_result(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");

		dealer.cleanDeckOfCards();
		var dealAllCards1 = dealer.dealAllCards();

		dealer.cleanDeckOfCards();
		var dealAllCards2 = dealer.dealAllCards();

		assertIterableEquals(dealAllCards1, dealAllCards2);
	}

	/**
	 * Check open deck cards get diferent result after shuffle.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_open_deck_cards_get_diferent_result_after_shuffle(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		dealer.cleanDeckOfCards();
		var last = dealer.dealAllCards();

		for (var counter = 0; counter < 10; counter++) {
			dealer.shuffle();
			
			var current = dealer.dealAllCards();

			assertTrue(!Arrays.equals(last.toArray(), current.toArray()));

			last = current;
		}
	}

	/**
	 * Check that you cannot shuffle while cards are being dealt.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_you_cannot_shuffle_while_cards_are_being_dealt(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		dealer.cleanDeckOfCards();

		dealer.shuffle();

		// Deal some cards
		for (var i = 0; i < 10; i++) {
			dealer.dealOneCard();
		}

		assertThrows(DealerOperationNotPermittedException.class, () -> {
			dealer.shuffle();
		});
	}

	/**
	 * Check that you cannot dealt while cards are shuffle.
	 *
	 * @param testInfo the test info
	 * @throws InterruptedException the interrupted exception
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_you_cannot_dealt_while_cards_are_shuffle(final Dealer dealer, String name, TestInfo testInfo) throws InterruptedException {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");

		dealer.cleanDeckOfCards();

		dealer.setShuffleRepetitions(200000);
		
		final var startSignal = new CountDownLatch(1);
		var shuffleThread = new Thread(() -> {
			logger.info("Start shuffle");
			startSignal.countDown();
			dealer.shuffle();
			logger.info("End shuffle");
		});

		logger.info("Starting Shuffle Thread...");
		shuffleThread.start();
		startSignal.await();		
		logger.info("Started Shuffle Thread...");

		assertThrows(DealerOperationNotPermittedException.class, () -> {
			logger.info("Start dealOneCard...");
			dealer.dealOneCard();
		});

		shuffleThread.join();
	}

	/**
	 * Check that you cannot dealt all while cards are shuffle.
	 *
	 * @param testInfo the test info
	 * @throws InterruptedException the interrupted exception
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_you_cannot_dealt_all_while_cards_are_shuffle(final Dealer dealer, String name, TestInfo testInfo) throws InterruptedException {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");

		dealer.cleanDeckOfCards();

		dealer.setShuffleRepetitions(200000);

		final var startSignal = new CountDownLatch(1);
		var shuffleThread = new Thread(() -> {
			logger.info("Start shuffle");
			startSignal.countDown();
			dealer.shuffle();
			logger.info("End shuffle");
		});

		logger.info("Starting Shuffle Thread...");
		shuffleThread.start();
		startSignal.await();		
		logger.info("Started Shuffle Thread...");

		assertThrows(DealerOperationNotPermittedException.class, () -> {
			logger.info("Start dealAllCards...");
			dealer.dealAllCards();
		});

		shuffleThread.join();
	}

	/**
	 * Check that you can shuffle if cards are not being dealt.
	 *
	 * @param testInfo the test info
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_that_you_can_shuffle_if_cards_are_not_being_dealt(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		dealer.cleanDeckOfCards();

		dealer.shuffle();

		// Deal some cards
		for (var i = 0; i < 10; i++) {			
			logger.info("Some cards: " + dealer.dealOneCard().get());
		}

		dealer.cleanDeckOfCards();

		dealer.shuffle();

		assertTrue(dealer.dealOneCard().isPresent());
	}
	
	
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_ShuffleRepetions(final Dealer dealer, String name, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		assertEquals(10, dealer.getShuffleRepetitions());
	}
	
	
	/**
	 * Check shuffle performance.
	 *
	 * @return the stream
	 */
	private static Stream<Arguments> dealers() {
		return Stream.of(
				Arguments.arguments(new DealerImpl(), 				"DealerImpl"			),
				Arguments.arguments(new DealerListShuffleImpl(), 	"DealerListShuffleImpl"	),
				Arguments.arguments(new DealerProvidedLiblmpl(), 	"DealerProvidedLiblmpl"	),
				Arguments.arguments(new DealerArrayShuffleImpl(), 	"DealerArrayShufflImpl"	)
		);
	}

}

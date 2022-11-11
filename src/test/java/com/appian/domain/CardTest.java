package com.appian.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.appian.domain.enums.NumberCard;
import com.appian.domain.enums.SuitCard;

/**
 * The Class CardTest.
 */
public class CardTest {

	/** The logger. */
	private static Logger logger = Logger.getLogger(CardTest.class);

	/**
	 * Check if two cards are equals.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_two_cards_are_equals(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);
		Card card2 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		assertEquals(card1, card2);
	}

	/**
	 * Check if two cards are the same.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_two_cards_are_the_same(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		assertEquals(card1, card1);
	}

	/**
	 * Check if two card are diferent with number.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_two_card_are_diferent_with_Number(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);
		Card card2 = new Card(SuitCard.CLUBS, NumberCard.NINE);

		assertNotEquals(card1, card2);
	}

	/**
	 * Check if two card are diferent with suit.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_two_card_are_diferent_with_Suit(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);
		Card card2 = new Card(SuitCard.DIAMONDS, NumberCard.EIGHT);

		assertNotEquals(card1, card2);
	}

	/**
	 * Check if two card are diferent with null.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_two_card_are_diferent_with_null(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		assertNotEquals(card1, null);
	}

	/**
	 * Check if two card are diferent object.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_two_card_are_diferent_object(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		assertNotEquals(card1, new String());
	}

	/**
	 * Check if attribs are correct setted by constructor.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_if_attribs_are_correct_setted_by_constructor(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		assertEquals(card1.getNumberCard(), NumberCard.EIGHT);
		assertEquals(card1.getSuitCard(), SuitCard.CLUBS);
	}

	/**
	 * Check to string.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_to_String(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		String card1ToString = "Card [" + NumberCard.EIGHT.getNumber() + " of " + SuitCard.CLUBS.getName() + "]";

		assertEquals(card1ToString, card1.toString());
	}

	/**
	 * Check hash code different to zero.
	 *
	 * @param testInfo the test info
	 */
	@Test
	public void Check_hash_code_different_to_zero(TestInfo testInfo) {
		logger.info("--< " + testInfo.getTestMethod().get().getName() + " >--");

		Card card1 = new Card(SuitCard.CLUBS, NumberCard.EIGHT);

		assertNotEquals(0, card1.hashCode());
		assertEquals(Objects.hash(NumberCard.EIGHT, SuitCard.CLUBS), card1.hashCode());
	}

}

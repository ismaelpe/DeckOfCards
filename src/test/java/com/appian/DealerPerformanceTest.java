package com.appian;

import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.appian.service.Dealer;
import com.appian.service.impl.DealerArrayShuffleImpl;
import com.appian.service.impl.DealerProvidedLiblmpl;
import com.appian.service.impl.DealerImpl;
import com.appian.service.impl.DealerListShuffleImpl;

/**
 * The Class DealerPerformanceTest.
 */
public class DealerPerformanceTest {

	/** The logger. */
	private static Logger logger = Logger.getLogger(DealerPerformanceTest.class);

	/**
	 * Check shuffle performance.
	 *
	 * @param name       the name
	 * @param dealer     the dealer
	 * @param iterations the iterations
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_shuffle_performance(final Dealer dealer, String name,  int iterations, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		long totalTimeMillis = 0L;
		for (var index = 0; index < iterations; index++) {
			long startTimeMillis = System.currentTimeMillis();
			dealer.shuffle();
			totalTimeMillis += System.currentTimeMillis() - startTimeMillis;
		}

		float totalMeanTimeMillis = (float) totalTimeMillis / (float) iterations;

		logger.info(totalMeanTimeMillis + "ms");
	}

	/**
	 * Check deal all cards performance.
	 *
	 * @param name       the name
	 * @param dealer     the dealer
	 * @param iterations the iterations
	 */
	@ParameterizedTest
	@MethodSource("dealers")
	public void Check_deal_all_cards_performance(final Dealer dealer, String name, int iterations, TestInfo testInfo) {
		logger.info("--< " + name + ": " + testInfo.getTestMethod().get().getName() + " >--");
		
		long totalTimeMillis = 0L;
		for (var index = 0; index < iterations; index++) {
			dealer.cleanDeckOfCards();
			long startTimeMillis = System.currentTimeMillis();
			dealer.dealAllCards();
			totalTimeMillis += System.currentTimeMillis() - startTimeMillis;
		}

		float totalMeanTimeMillis = (float) totalTimeMillis / (float) iterations;

		logger.info(totalMeanTimeMillis + "ms");
	}

	/**
	 * Check shuffle performance.
	 *
	 * @return the stream
	 */
	private static Stream<Arguments> dealers() {
		return Stream.of(
				Arguments.arguments(new DealerImpl(), 				"DealerImpl",  				2000),
				Arguments.arguments(new DealerListShuffleImpl(), 	"DealerListShuffleImpl",	2000),
				Arguments.arguments(new DealerProvidedLiblmpl(), 	"DealerProvidedLiblmpl", 	2000),
				Arguments.arguments(new DealerArrayShuffleImpl(), 	"DealerArrayShuffleImpl", 	2000)
		);
	}

}

package com.nurkiewicz.asyncretry.backoff;

import com.nurkiewicz.asyncretry.AbstractBaseTestCase;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * @author Tomasz Nurkiewicz
 * @since 7/20/13, 5:30 PM
 */
public class ExponentialDelayBackoffTest extends AbstractBaseTestCase {

	@Test
	public void shouldThrowWhenNotPositiveInitialDelay() throws Exception {
		//given
		final int initialDelayMillis = 0;

		try {
			//when
			new ExponentialDelayBackoff(initialDelayMillis, 2.0);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException e) {
			//then
			assertThat(e.getMessage()).endsWith("0");
		}

	}

	@Test
	public void shouldReturnPowersOfTwo() throws Exception {
		//given
		final ExponentialDelayBackoff backoff = new ExponentialDelayBackoff(1, 2.0);

		//when
		final long first = backoff.delayMillis(retry(1));
		final long second = backoff.delayMillis(retry(2));
		final long third = backoff.delayMillis(retry(3));
		final long fourth = backoff.delayMillis(retry(4));

		//then
		assertThat(first).isEqualTo(1);
		assertThat(second).isEqualTo(2);
		assertThat(third).isEqualTo(2 * 2);
		assertThat(fourth).isEqualTo(2 * 2 * 2);
	}

}

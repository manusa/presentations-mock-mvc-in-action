/*
 * RandomNumberServiceTest.java
 *
 * Created on 2019-02-20, 10:32
 */
package com.marcnuri.demo.mockmvcinaction.webflux.random;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-20.
 */
public class RandomNumberServiceTest {

  private RandomNumberService randomNumberService;

  @Before
  public void setUp() {
    randomNumberService = new RandomNumberService();
  }

  @After
  public void tearDown() {
    randomNumberService = null;
  }

  @Test
  public void getRandomRumbers_shouldReturnFluxWithInterval() {
    StepVerifier.withVirtualTime(randomNumberService::getRandomNumbers, 1)
        .thenAwait(Duration.ofMillis(500))
        .assertNext(value -> assertThat(value, allOf(
            greaterThanOrEqualTo(0D),
            lessThan(1D))))
        .thenCancel()
        .verify();
  }

  @Test
  public void getRandomNumber_shouldReturnDouble() {
    // When
    final Double result = randomNumberService.getRandomNumber();

    // Then
    assertThat(result, not(nullValue()));
    assertThat(result, greaterThanOrEqualTo(0D));
    assertThat(result, lessThan(1D));
  }
}

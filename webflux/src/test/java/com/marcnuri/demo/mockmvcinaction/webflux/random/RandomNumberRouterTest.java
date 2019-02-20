/*
 * RandomNumberRouterTest.java
 *
 * Created on 2019-02-19, 21:46
 */
package com.marcnuri.demo.mockmvcinaction.webflux.random;

import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import java.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-19.
 */
public class RandomNumberRouterTest {

  private RandomNumberService mockRandomNumberService;
  private WebTestClient webTestClient;

  @Before
  public void setUp() {
    mockRandomNumberService = Mockito.mock(RandomNumberService.class);

    webTestClient = WebTestClient
        .bindToRouterFunction(RandomNumberRouter.randomNumberRouters(mockRandomNumberService))
        .configureClient()
        .build();
  }

  @After
  public void tearDown() {
    webTestClient = null;
  }

  @Test
  public void randomNumberRouters_validRequest_shouldReturnOk() {
    // Given
    doReturn(
        Flux.just(1337D, 313373D)
    ).when(mockRandomNumberService).getRandomNumbers();


    // When
    final ResponseSpec result = webTestClient.get()
        .uri("/functional/random")
        .accept(TEXT_EVENT_STREAM)
        .exchange();

    // Then
    result.expectStatus().isOk();
    final Flux<Double> content = result.returnResult(Double.class).getResponseBody();
    StepVerifier.create(content)
        .expectSubscription()
        .expectNext(1337D, 313373D)
        .verifyComplete();
  }

  @Test
  public void test() {
    StepVerifier.withVirtualTime(() ->
        Flux.interval(Duration.ofMillis(100))
            .zipWith(Flux.just(1 , 2, 3, 4), (i, user) -> user))
        .expectSubscription()
        .thenRequest(4)
        .thenAwait(Duration.ofMillis(400))
        .thenCancel()
        .verify();
  }
}

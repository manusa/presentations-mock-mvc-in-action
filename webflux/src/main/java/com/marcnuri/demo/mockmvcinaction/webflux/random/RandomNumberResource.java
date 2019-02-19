/*
 * RandomNumberResource.java
 *
 * Created on 2019-02-19, 17:00
 */
package com.marcnuri.demo.mockmvcinaction.webflux.random;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-19.
 */
@RestController
@RequestMapping(path = "/random")
public class RandomNumberResource {

  private final RandomNumberService randomNumberService;

  public RandomNumberResource(RandomNumberService randomNumberService) {
    this.randomNumberService = randomNumberService;
  }

  @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Double> getRandomNumbers() {
    return randomNumberService.getRandomNumbers();
  }
}

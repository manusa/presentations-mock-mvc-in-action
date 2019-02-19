/*
 * RandomNumberService.java
 *
 * Created on 2019-02-02, 19:13
 */
package com.marcnuri.demo.mockmvcinaction.webflux.random;

import java.time.Duration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-02.
 */
@Service
public class RandomNumberService {

  public Flux<Double> getRandomNumbers() {
    return Flux.interval(Duration.ofMillis(500)).map(i -> getRandomNumber());
  }

  public Double getRandomNumber() {
    return Math.random();
  }
}

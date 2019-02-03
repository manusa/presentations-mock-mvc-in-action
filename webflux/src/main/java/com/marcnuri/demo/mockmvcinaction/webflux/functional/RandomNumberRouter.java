/*
 * RandomNumberRouter.java
 *
 * Created on 2019-02-02, 19:12
 */
package com.marcnuri.demo.mockmvcinaction.webflux.functional;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-02.
 */
@Configuration
public class RandomNumberRouter {

  @Bean(autowireCandidate = false)
  public static RouterFunction<ServerResponse> randomNumberRouters(RandomNumberService randomNumberService) {
    final Flux<Double> test =  Flux.interval(Duration.ofMillis(500))
        .map(i -> randomNumberService.getRandomNumber());
    return route(GET("/random"),
        request -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(test, Double.class));
  }
}

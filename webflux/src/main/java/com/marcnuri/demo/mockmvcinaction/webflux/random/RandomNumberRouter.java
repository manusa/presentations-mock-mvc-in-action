/*
 * RandomNumberRouter.java
 *
 * Created on 2019-02-02, 19:12
 */
package com.marcnuri.demo.mockmvcinaction.webflux.random;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-02.
 */
@Configuration
public class RandomNumberRouter {

  @Bean(autowireCandidate = false)
  public static RouterFunction<ServerResponse> randomNumberRouters(RandomNumberService randomNumberService) {
    return route(GET("/functional/random"),
        request -> ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(randomNumberService.getRandomNumbers(), Double.class));
  }
}

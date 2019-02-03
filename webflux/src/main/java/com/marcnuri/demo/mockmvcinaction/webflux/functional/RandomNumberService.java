/*
 * RandomNumberService.java
 *
 * Created on 2019-02-02, 19:13
 */
package com.marcnuri.demo.mockmvcinaction.webflux.functional;

import org.springframework.stereotype.Service;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-02.
 */
@Service
public class RandomNumberService {
  public Double getRandomNumber() {
    return Math.random();
  }
}

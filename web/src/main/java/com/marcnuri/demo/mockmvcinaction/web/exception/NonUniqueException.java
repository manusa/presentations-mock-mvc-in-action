/*
 * NonUniqueException.java
 *
 * Created on 2019-02-13, 7:10
 */
package com.marcnuri.demo.mockmvcinaction.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-13.
 */
public class NonUniqueException extends BusinessException {

  public NonUniqueException(String message) {
    super(message, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}

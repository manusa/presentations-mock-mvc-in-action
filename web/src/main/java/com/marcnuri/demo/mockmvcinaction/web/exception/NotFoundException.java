/*
 * NotFoundException.java
 *
 * Created on 2019-02-10, 17:34
 */
package com.marcnuri.demo.mockmvcinaction.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-10.
 */
public class NotFoundException extends BusinessException {

  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }
}

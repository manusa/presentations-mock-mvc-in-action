/*
 * BusinessException.java
 *
 * Created on 2019-02-10, 17:30
 */
package com.marcnuri.demo.mockmvcinaction.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-10.
 */
public class BusinessException extends RuntimeException {

  private final HttpStatus status;

  public BusinessException(String message, @NonNull HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}

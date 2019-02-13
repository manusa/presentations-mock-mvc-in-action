/*
 * BusinessExceptionControllerAdvice.java
 *
 * Created on 2019-02-10, 17:31
 */
package com.marcnuri.demo.mockmvcinaction.web.exception;

import com.marcnuri.demo.mockmvcinaction.web.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Marc Nuri <marc@marcnuri.com> on 2019-02-10.
 */
@ControllerAdvice(basePackageClasses = Configuration.class)
public class BusinessExceptionControllerAdvice {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> businessException(BusinessException businessException) {
    return ResponseEntity.status(businessException.getStatus())
        .header("Warning", "199 - \"" + businessException.getLocalizedMessage() + "\"")
        .body(businessException.getMessage());
  }
}

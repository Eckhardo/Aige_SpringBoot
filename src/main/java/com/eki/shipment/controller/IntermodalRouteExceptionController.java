/**
 * 
 */
package com.eki.shipment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author eckhard kirschning
 *
 */
public class IntermodalRouteExceptionController {
	   @ExceptionHandler(value = IntermodalRouteNotFoundException.class)
	   public ResponseEntity<Object> exception(IntermodalRouteNotFoundException exception) {
	      return new ResponseEntity<>("Key Figure not found", HttpStatus.NOT_FOUND);
	   }
}

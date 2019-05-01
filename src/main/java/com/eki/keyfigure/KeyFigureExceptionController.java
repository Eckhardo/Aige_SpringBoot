/**
 * 
 */
package com.eki.keyfigure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author eckhard kirschning
 *
 */
public class KeyFigureExceptionController {
	   @ExceptionHandler(value = KeyFigureNotFoundException.class)
	   public ResponseEntity<Object> exception(KeyFigureNotFoundException exception) {
	      return new ResponseEntity<>("Key Figure not found", HttpStatus.NOT_FOUND);
	   }
}

package com.jakal.web;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@ControllerAdvice
public class ExceptionHandlerController {
	Logger log = LoggerFactory.getLogger(this.getClass());
		
	@ExceptionHandler({SQLException.class,DataAccessException.class})
	public ResponseEntity<String> databaseError(Exception e) {
		log.error("DataBase exception: ", e);
		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body("Could not process this request due to data validation error.");
  }
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<String> allOtherErrors(Exception e) {
		log.error("Some exception: ", e);
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Could not process this request due to an unknown error.");
  }
}

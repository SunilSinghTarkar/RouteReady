package com.routeready.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetails> NotFoundException(NotFoundException ex, WebRequest req) {
		ErrorDetails error = new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RouteReadyException.class)
	public ResponseEntity<ErrorDetails> RouteReadyException(RouteReadyException ex, WebRequest req) {
		ErrorDetails error = new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> RuntimeException(RuntimeException ex, WebRequest req) {
		ErrorDetails error = new ErrorDetails(ex.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> myExceptionHandler(MethodArgumentNotValidException me) {

		ErrorDetails err = new ErrorDetails("Validation Error",
				me.getBindingResult().getFieldError().getDefaultMessage(), LocalDateTime.now());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	
	
	
	
	
}

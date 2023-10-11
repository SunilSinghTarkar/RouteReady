package com.routeready.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// to handle specific InvalidInputException
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<MyErrorDetails> myInputExceptionHandler(InvalidInputException ie, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), ie.getMessage(), req.getDescription(false));

		ResponseEntity<MyErrorDetails> re = new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

		return re;
	}

	// to handle specific NotFoundException
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<MyErrorDetails> myNotFoundHandler(NotFoundException ie, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), ie.getMessage(), req.getDescription(false));

		ResponseEntity<MyErrorDetails> re = new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

		return re;
	}

	// to handle specific RouteReadyException
	@ExceptionHandler(RouteReadyException.class)
	public ResponseEntity<MyErrorDetails> myRideEasyHandler(RouteReadyException ie, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), ie.getMessage(), req.getDescription(false));

		ResponseEntity<MyErrorDetails> re = new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

		return re;
	}

	// to handle Not found exception
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> mynotFoundHandler(NoHandlerFoundException nfe, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), nfe.getMessage(), req.getDescription(false));

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest req) {

		MyErrorDetails error = new MyErrorDetails(LocalDateTime.now(), ex.getMessage(), req.getDescription(false));

		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	// Validation Error Handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> myMANVExceptionHandler(MethodArgumentNotValidException me) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), "Validation Error",
				me.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	// to handle generic any type of Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception e, WebRequest req) {

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

}

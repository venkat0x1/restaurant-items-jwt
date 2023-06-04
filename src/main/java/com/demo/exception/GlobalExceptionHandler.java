package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),exception.getMessage(),request.getDescription(false) );
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ArgumentsMismatchException.class)
    public ResponseEntity<ErrorResponse> handleArgumentsMismatchException(ArgumentsMismatchException exception, WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),exception.getMessage(),request.getDescription(false) );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUserUnauthorizedException(UserUnauthorizedException exception, WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(),exception.getMessage(),request.getDescription(false) );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception,WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),exception.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<ErrorResponse> handleVerificationException(VerificationException exception,WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.CONFLICT.value(),exception.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception,WebRequest request){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),exception.getMessage(),request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

package com.udemy.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)

    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(CustomErrorResponse.builder()
                        .message(e.getMessage())
                        .dateTime(LocalDateTime.now())
                        .status(HttpStatus.CONFLICT)
                .build());
    }

    @ExceptionHandler(PhoneNumberIsNotVerified.class)
    public ResponseEntity<CustomErrorResponse> handlePhoneNumberIsNotVerified(PhoneNumberIsNotVerified e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomErrorResponse.builder()
                        .message(e.getMessage())
                        .dateTime(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                .build());
    }
    @ExceptionHandler({SignatureException.class})
    public ResponseEntity<CustomErrorResponse> handleSignatureException(SignatureException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomErrorResponse.builder()
                        .message(e.getMessage())
                        .dateTime(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED)
                .build());
    }

}

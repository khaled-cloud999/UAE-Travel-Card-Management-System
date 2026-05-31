package com.demo.travelcardsystem.controller;

import com.demo.travelcardsystem.exception.InvalidCardException;
import com.demo.travelcardsystem.exception.InvalidDataProvidedException;
import com.demo.travelcardsystem.exception.InvalidRechargeAmount;
import com.demo.travelcardsystem.exception.TravelCardException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler({InvalidCardException.class, InvalidRechargeAmount.class})
    public ResponseEntity<String> handleInvalidRequestException(TravelCardException exception) {
        log.warn("Invalid request: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidDataProvidedException.class)
    public ResponseEntity<String> handleInvalidDataProvidedException(InvalidDataProvidedException exception) {
        log.warn("Invalid data provided: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request! Please check input");
    }
}

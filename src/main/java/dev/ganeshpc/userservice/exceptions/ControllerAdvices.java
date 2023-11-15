package dev.ganeshpc.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.ganeshpc.userservice.dtos.ErrorDto;

@ControllerAdvice
public class ControllerAdvices {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e) {

        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }
}

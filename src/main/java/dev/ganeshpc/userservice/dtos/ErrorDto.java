package dev.ganeshpc.userservice.dtos;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
    private HttpStatus httpStatus;
    private String message;

    public ErrorDto(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message =message;
    }
}

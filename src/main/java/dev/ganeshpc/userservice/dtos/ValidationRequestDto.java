package dev.ganeshpc.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationRequestDto {
    private String token;
    private Long userId;
}

package dev.ganeshpc.userservice.dtos;


import dev.ganeshpc.userservice.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionResponseDto {
    Long userId;
    String token;
    String emailId;
    SessionStatus sessionStatus;
}


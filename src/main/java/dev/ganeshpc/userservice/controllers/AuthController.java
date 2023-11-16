package dev.ganeshpc.userservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import dev.ganeshpc.userservice.dtos.LogoutRequestDto;
import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.dtos.SessionResponseDto;
import dev.ganeshpc.userservice.dtos.ValidationRequestDto;
import dev.ganeshpc.userservice.exceptions.InvalidCredentialException;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.SessionStatus;
import dev.ganeshpc.userservice.services.AuthService;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<SessionResponseDto> login(@RequestBody RequestUserDto requestUserDto)
            throws UserNotFoundException, InvalidCredentialException {
        SessionResponseDto sessionResponseDto = authService.login(requestUserDto.getEmailId(),
                requestUserDto.getPassword());

        String token = sessionResponseDto.getToken();

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        ResponseEntity<SessionResponseDto> response = new ResponseEntity<>(sessionResponseDto, headers, HttpStatus.OK);

        return response;
    }

    @PostMapping("logout")
    public void logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId());
    }

    @PostMapping(value = "signup")
    public ResponseUserDto signup(@RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto responseUserDto = authService.signup(requestUserDto.getEmailId(), requestUserDto.getPassword());
        return responseUserDto;
    }

    @PostMapping(value = "validate")
    public String postMethodName(@RequestBody ValidationRequestDto validationRequestDto) {

        SessionStatus sessionStatus = authService.validate(validationRequestDto.getToken(),
                validationRequestDto.getUserId());

        return sessionStatus.toString();
    }

}

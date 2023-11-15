package dev.ganeshpc.userservice.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.dtos.SessionResponseDto;
import dev.ganeshpc.userservice.exceptions.InvalidCredentialException;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.Session;
import dev.ganeshpc.userservice.models.SessionStatus;
import dev.ganeshpc.userservice.models.User;
import dev.ganeshpc.userservice.repositories.SessionRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private SessionRepository sessionRepository;
    private UserService userService;

    public AuthService(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    public SessionResponseDto login(String emailId, String password)
            throws InvalidCredentialException, UserNotFoundException {
        User user = userService.getUserByEmailId(emailId);

        if (!password.equals(user.getPassword())) {
            throw new InvalidCredentialException(emailId);
        }

        String token = generateRandomString();

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);

        sessionRepository.save(session);

        SessionResponseDto sessionResponseDto = new SessionResponseDto();
        sessionResponseDto.setEmailId(emailId);
        sessionResponseDto.setToken(token);
        sessionResponseDto.setUserId(user.getId());
        sessionResponseDto.setSessionStatus(SessionStatus.ACTIVE);

        return sessionResponseDto;
    }

    @Transactional
    public void logout(String token, Long userId) {
        sessionRepository.deleteByTokenAndUser_Id(token, userId);
    }

    public ResponseUserDto signup(String emailId, String password) {
        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setEmailId(emailId);
        requestUserDto.setPassword(password);
        return userService.createUser(requestUserDto);
    }

    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            return session.getSessionStatus();
        } else {
            return SessionStatus.ENDED;
        }
    }

    private String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        // Remove hyphens and return the substring
        return uuid.replaceAll("-", "");
    }

}

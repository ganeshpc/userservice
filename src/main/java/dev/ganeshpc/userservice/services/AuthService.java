package dev.ganeshpc.userservice.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.dtos.SessionResponseDto;
import dev.ganeshpc.userservice.exceptions.InvalidCredentialException;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.Role;
import dev.ganeshpc.userservice.models.Session;
import dev.ganeshpc.userservice.models.SessionStatus;
import dev.ganeshpc.userservice.models.User;
import dev.ganeshpc.userservice.repositories.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private SessionRepository sessionRepository;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(SessionRepository sessionRepository, UserService userService,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public SessionResponseDto login(String emailId, String password)
            throws InvalidCredentialException, UserNotFoundException {
        User user = userService.getUserByEmailId(emailId);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialException(emailId);
        }

        MacAlgorithm algo = Jwts.SIG.HS256;
        SecretKey key = algo.key().build();

        Map<String, Object> jsonForJwt = new HashMap<>();

        jsonForJwt.put("emailId", user.getEmailId());
        jsonForJwt.put("roles", user.getRoles());
        jsonForJwt.put("createdAt", new Date());
        jsonForJwt.put("expiryAt", new Date());

        String jws = Jwts.builder().claims(jsonForJwt)
                .signWith(key, algo).compact();

        Session session = new Session();
        session.setUser(user);
        session.setToken(jws);
        session.setSessionStatus(SessionStatus.ACTIVE);

        sessionRepository.save(session);

        SessionResponseDto sessionResponseDto = new SessionResponseDto();
        sessionResponseDto.setEmailId(emailId);
        sessionResponseDto.setToken(jws);
        sessionResponseDto.setUserId(user.getId());
        sessionResponseDto.setSessionStatus(SessionStatus.ACTIVE);

        return sessionResponseDto;
    }

    @Transactional
    public void logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return;
        }

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    public ResponseUserDto signUp(String emailId, String password) {
        RequestUserDto requestUserDto = new RequestUserDto();
        requestUserDto.setEmailId(emailId);
        requestUserDto.setPassword(bCryptPasswordEncoder.encode(password));
        requestUserDto.setPassword(password);
        return userService.createUser(requestUserDto);
    }

    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        // Jws<Claims> claimsJws = Jwts.parser()
        // .build().parse

        // String email = (String) claimsJws.getPayload().get("emailId");
        // List<Role> roles = (List<Role>) claimsJws.getPayload().get("roles");
        // Date createdAt = (Date) claimsJws.getPayload().get("createdAt");

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

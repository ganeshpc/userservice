package dev.ganeshpc.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ganeshpc.userservice.models.Session;
import java.util.Optional;


public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
    void deleteByTokenAndUser_Id(String token, Long userId);
}

package dev.ganeshpc.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ganeshpc.userservice.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

}

package dev.ganeshpc.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ganeshpc.userservice.models.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailId(String emailId);
}

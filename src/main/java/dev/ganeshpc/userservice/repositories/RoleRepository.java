package dev.ganeshpc.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.ganeshpc.userservice.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}

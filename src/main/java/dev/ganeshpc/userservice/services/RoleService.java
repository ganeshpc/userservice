package dev.ganeshpc.userservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.ganeshpc.userservice.models.Role;
import dev.ganeshpc.userservice.repositories.RoleRepository;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(String name) {
        Role role = new Role();
        role.setRole(name);
        Role savedRole = roleRepository.save(role);
        return savedRole;
    }

    public List<Role> getRolesWithIds(List<Long> roleIds) {
        List<Role> roles = roleRepository.findAllById(roleIds);

        return roles;
    }
}

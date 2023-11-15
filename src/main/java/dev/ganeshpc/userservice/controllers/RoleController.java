package dev.ganeshpc.userservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ganeshpc.userservice.dtos.RequestRoleDto;
import dev.ganeshpc.userservice.models.Role;
import dev.ganeshpc.userservice.services.RoleService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;
    
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping()
    public Role createRole(@RequestBody RequestRoleDto requestRoleDto) {
        Role role = roleService.createRole(requestRoleDto.getName());
        return role;
    }
}

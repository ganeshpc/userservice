package dev.ganeshpc.userservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.dtos.SetRolesRequestDto;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.User;
import dev.ganeshpc.userservice.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<ResponseUserDto> getUsers() {
        List<ResponseUserDto> users = userService.getUsers();
        return users;
    }

    @GetMapping("{id}")
    public ResponseUserDto getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        ResponseUserDto responseUserDto = userService.getUser(id);
        return responseUserDto;
    }

    @PostMapping()
    public ResponseUserDto createUser(@RequestBody RequestUserDto requestUserDto) {
        ResponseUserDto responseUserDto = userService.createUser(requestUserDto);
        return responseUserDto;
    }

    @PostMapping(value = "{id}/roles")
    public ResponseUserDto postMethodName(@PathVariable("id") Long id,
            @RequestBody SetRolesRequestDto setRolesRequestDto) throws UserNotFoundException {
        ResponseUserDto responseUserDto = userService.setRoles(id, setRolesRequestDto.getRoles());
        return responseUserDto;
    }

}

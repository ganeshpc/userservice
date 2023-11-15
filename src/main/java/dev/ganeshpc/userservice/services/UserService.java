package dev.ganeshpc.userservice.services;

import java.util.List;

import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.Session;

public interface UserService {
    List<ResponseUserDto> getUsers();
    ResponseUserDto getUser(Long id) throws UserNotFoundException;
    Session login(RequestUserDto requestUserDto);
    void logout(Session session);
    ResponseUserDto createUser(RequestUserDto requestUserDto);
}

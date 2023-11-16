package dev.ganeshpc.userservice.services;

import java.util.List;
import java.util.Optional;

import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.User;

public interface UserService {
    List<ResponseUserDto> getUsers();
    ResponseUserDto getUser(Long id) throws UserNotFoundException;
    ResponseUserDto createUser(RequestUserDto requestUserDto);
    User getUserByEmailId(String emailId) throws UserNotFoundException;
    ResponseUserDto setRoles(Long userId, List<Long> roles) throws UserNotFoundException;
}

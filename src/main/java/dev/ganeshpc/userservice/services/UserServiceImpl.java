package dev.ganeshpc.userservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ganeshpc.userservice.dtos.RequestUserDto;
import dev.ganeshpc.userservice.dtos.ResponseUserDto;
import dev.ganeshpc.userservice.exceptions.UserNotFoundException;
import dev.ganeshpc.userservice.models.Session;
import dev.ganeshpc.userservice.models.User;
import dev.ganeshpc.userservice.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<ResponseUserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<ResponseUserDto> responseUserDtos = new ArrayList<>();
        for (User user : users) {
            responseUserDtos.add(roResponseUserDto(user));
        }
        return responseUserDtos;
    }

    @Override
    public ResponseUserDto getUser(Long id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(id);
        }

        User user = optionalUser.get();
        ResponseUserDto responseUserDto = ResponseUserDto.toResponseUserDto(user);
        return responseUserDto;
    }

    @Override
    public Session login(RequestUserDto requestUserDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public void logout(Session session) {
        // TODO Auto-generated method stub

    }

    @Override
    public ResponseUserDto createUser(RequestUserDto requestUserDto) {
        User user = requestUserDto.toUser();
        User savedUser = userRepository.save(user);
        return roResponseUserDto(savedUser);
    }

    public ResponseUserDto roResponseUserDto(User user) {
        ResponseUserDto responseUserDto = new ResponseUserDto();

        responseUserDto.setId(user.getId());
        responseUserDto.setEmailId(user.getEmailId());

        return responseUserDto;
    }
}

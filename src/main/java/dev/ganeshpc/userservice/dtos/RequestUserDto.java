package dev.ganeshpc.userservice.dtos;

import dev.ganeshpc.userservice.models.User;
import lombok.Data;

@Data
public class RequestUserDto {

    private Long id;

    private String emailId;

    private String password;

    public User toUser() {
        User user = new User();

        user.setId(id);
        user.setEmailId(emailId);
        user.setPassword(password);

        return user;
    }
}

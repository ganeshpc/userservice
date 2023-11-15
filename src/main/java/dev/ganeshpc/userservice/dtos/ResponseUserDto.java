package dev.ganeshpc.userservice.dtos;

import dev.ganeshpc.userservice.models.Session;
import dev.ganeshpc.userservice.models.User;
import lombok.Data;

@Data
public class ResponseUserDto {

    private Long id;

    private String emailId;


    User toUser() {
        User user = new User();

        user.setId(id);
        user.setEmailId(emailId);

        return user;
    }

    public static ResponseUserDto toResponseUserDto(User user) {
        ResponseUserDto responseUserDto = new ResponseUserDto();

        responseUserDto.setId(user.getId());
        responseUserDto.setEmailId(user.getEmailId());

        return responseUserDto;
    }
}

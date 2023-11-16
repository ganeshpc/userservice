package dev.ganeshpc.userservice.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SetRolesRequestDto {
    List<Long> roles;
}

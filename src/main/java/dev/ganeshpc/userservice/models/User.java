package dev.ganeshpc.userservice.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User extends BaseModel {

    private String emailId;

    private String password;

    @OneToMany
    private List<Session> sessions;
}

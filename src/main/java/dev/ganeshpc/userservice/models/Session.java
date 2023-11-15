package dev.ganeshpc.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Session extends BaseModel {
    
    private String token;

    @ManyToOne
    private User user;
}

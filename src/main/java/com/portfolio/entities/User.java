package com.portfolio.entities;

import com.portfolio.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User extends BaseModel{

    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;
}

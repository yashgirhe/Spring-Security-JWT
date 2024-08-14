package com.example.security.jwt.dto;

import com.example.security.jwt.enums.Permission;
import com.example.security.jwt.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}

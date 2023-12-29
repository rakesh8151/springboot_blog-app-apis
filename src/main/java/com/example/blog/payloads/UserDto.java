package com.example.blog.payloads;

import com.example.blog.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty()
    @Size(min = 4,message = "Username must be min of 4 characters !!")
    private  String name;
    @Email(message = "your email address is not valid !!")
    private  String email;
    @NotEmpty
    @Size(min = 8,message = "password must be min of 4 characters")
    private  String password;
    @NotEmpty(message = "about is empty, so please mention it")
    private  String about;
    private Set<RoleDto> roles=new HashSet<>();
}

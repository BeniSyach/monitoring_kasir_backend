package com.beni.syncapp.dto;

import com.beni.syncapp.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
}
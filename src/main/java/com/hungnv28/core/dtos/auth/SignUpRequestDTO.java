package com.hungnv28.core.dtos.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpRequestDTO implements Serializable {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String country;
    private String dateOfBrith;
    private String role;

}

package com.hungnv28.core.controllers.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpRequest implements Serializable {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String country;
    private String role;
    private String dateOfBrith;
}

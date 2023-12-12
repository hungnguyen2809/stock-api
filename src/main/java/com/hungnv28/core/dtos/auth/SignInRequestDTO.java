package com.hungnv28.core.dtos.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignInRequestDTO implements Serializable {
    private String username;
    private String password;
}

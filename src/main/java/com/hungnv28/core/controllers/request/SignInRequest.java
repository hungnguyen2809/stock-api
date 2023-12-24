package com.hungnv28.core.controllers.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignInRequest implements Serializable {
    private String username;
    private String password;
}

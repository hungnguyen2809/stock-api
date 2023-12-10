package com.hungnv28.core.controllers.AuthControler.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthSignInRequest implements Serializable {
    private String username;
    private String password;
}

package com.hungnv28.core.controllers.AuthControler.response;

import com.hungnv28.core.entities.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SignInResponse implements Serializable {
    UsersEntity userInfo;
    String token;
    String refresh_token;
}

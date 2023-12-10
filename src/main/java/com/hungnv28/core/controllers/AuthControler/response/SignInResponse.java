package com.hungnv28.core.controllers.AuthControler.response;

import com.hungnv28.core.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse implements Serializable {
    Users userInfo;
    String token;
    String refresh_token;
}

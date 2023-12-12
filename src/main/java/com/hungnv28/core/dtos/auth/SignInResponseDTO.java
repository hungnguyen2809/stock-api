package com.hungnv28.core.dtos.auth;

import com.hungnv28.core.entities.UsersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class SignInResponseDTO implements Serializable {
    UsersEntity userInfo;
    String token;
    String refreshToken;
}

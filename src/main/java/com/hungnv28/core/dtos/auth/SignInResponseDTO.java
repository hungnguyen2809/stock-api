package com.hungnv28.core.dtos.auth;

import com.hungnv28.core.dtos.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class SignInResponseDTO implements Serializable {
    String token;
    String refreshToken;
    UserInfoDTO userInfo;
}

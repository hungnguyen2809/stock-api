package com.hungnv28.core.controllers.response;

import com.hungnv28.core.dtos.UserInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SignInResponse implements Serializable {
    String token;
    String refreshToken;
    UserInfoDTO userInfo;
}

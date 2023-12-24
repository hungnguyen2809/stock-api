package com.hungnv28.core.services;

import com.hungnv28.core.controllers.request.SignUpRequest;
import com.hungnv28.core.dtos.UserInfoDTO;
import com.hungnv28.core.models.UserInfo;

public interface UserService {
    UserInfoDTO loginUser(String username, String password) throws Exception;

    boolean checkUser(String username) throws Exception;

    boolean registerUser(SignUpRequest data) throws Exception;

    UserInfoDTO findUserById(String id, UserInfo userInfo) throws Exception;
}

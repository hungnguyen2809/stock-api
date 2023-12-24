package com.hungnv28.core.daos;

import com.hungnv28.core.controllers.request.SignUpRequest;
import com.hungnv28.core.dtos.UserInfoDTO;

public interface UserDAO {
    UserInfoDTO loginUser(String username, String password) throws Exception;

    boolean checkUser(String username) throws Exception;

    boolean registerUser(SignUpRequest data) throws Exception;

    UserInfoDTO findUserById(Integer id) throws Exception;
}
package com.hungnv28.core.daos;

import com.hungnv28.core.dtos.auth.SignUpRequestDTO;
import com.hungnv28.core.dtos.user.UserInfoDTO;

public interface UserDAO {
    UserInfoDTO loginUser(String username, String password) throws Exception;

    boolean checkUser(String username) throws Exception;

    boolean registerUser(SignUpRequestDTO data) throws Exception;

    UserInfoDTO findUserById(Integer id) throws Exception;
}
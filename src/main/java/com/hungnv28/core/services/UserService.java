package com.hungnv28.core.services;

import com.hungnv28.core.dtos.auth.SignUpRequestDTO;
import com.hungnv28.core.entities.UsersEntity;

public interface UserService {
    public UsersEntity loginUser(String username, String password) throws Exception;

    public boolean checkUser(String username) throws Exception;

    public boolean registerUser(SignUpRequestDTO data) throws Exception;

    public UsersEntity findUserById(String id) throws Exception;
}

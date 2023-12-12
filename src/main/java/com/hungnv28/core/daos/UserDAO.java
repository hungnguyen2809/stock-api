package com.hungnv28.core.daos;

import com.hungnv28.core.dtos.auth.SignUpRequestDTO;
import com.hungnv28.core.entities.UsersEntity;

public interface UserDAO {
    public UsersEntity loginUser(String username, String password) throws Exception;

    public boolean checkUser(String username) throws Exception;

    public boolean registerUser(SignUpRequestDTO data) throws Exception;

    public UsersEntity findUserById(Integer id) throws Exception;
}
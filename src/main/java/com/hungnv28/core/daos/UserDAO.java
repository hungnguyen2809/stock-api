package com.hungnv28.core.daos;

import com.hungnv28.core.controllers.AuthControler.request.AuthSignUpRequest;
import com.hungnv28.core.entities.Users;

public interface UserDAO {
    public Users loginUser(String username, String password) throws Exception;

    public boolean checkUser(String username) throws Exception;

    public boolean registerUser(AuthSignUpRequest data) throws Exception;
}
package com.hungnv28.core.services;

import com.hungnv28.core.entities.Users;

public interface UserService {
    public Users checkUser(String username, String password) throws Exception;
}

package com.hungnv28.core.daos;

import com.hungnv28.core.entities.Users;

public interface UserDAO {
    public Users checkUser(String username, String password) throws Exception;
}
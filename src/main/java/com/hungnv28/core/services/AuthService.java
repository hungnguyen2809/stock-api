package com.hungnv28.core.services;

public interface AuthService {
    public void signIn(String username, String password);

    public void signUp(String username, String password);
}

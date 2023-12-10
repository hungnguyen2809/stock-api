package com.hungnv28.core.services.impl;

import com.hungnv28.core.controllers.AuthControler.request.AuthSignUpRequest;
import com.hungnv28.core.daos.UserDAO;
import com.hungnv28.core.entities.Users;
import com.hungnv28.core.enums.RoleUser;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.services.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public Users loginUser(String username, String password) throws Exception {
        if (StringUtils.isEmpty(username)) {
            throw new ApiException("Tên tài khoản không được trống", "username", HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isEmpty(password)) {
            throw new ApiException("Mật khẩu không được trống", "password", HttpStatus.BAD_REQUEST);
        }

        Users users = userDAO.loginUser(username, password);
        if (users == null) {
            throw new ApiException("Thông tin tài khoản hoặc mật khẩu không chính xác", "user_not_found", HttpStatus.NOT_FOUND);
        }

        return users;
    }

    @Override
    public boolean checkUser(String username) throws Exception {
        if (StringUtils.isEmpty(username)) {
            throw new ApiException("Tên tài khoản không được trống", "username", HttpStatus.BAD_REQUEST);
        }

        return userDAO.checkUser(username);
    }

    @Override
    public boolean registerUser(AuthSignUpRequest data) throws Exception {
        if (StringUtils.isEmpty(data.getUsername())) {
            throw new ApiException("Tên tài khoản không được trống", "username", HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isEmpty(data.getPassword())) {
            throw new ApiException("Mật kẩu không được trống", "password", HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isNotEmpty(data.getRole())) {
            if (Arrays.stream(RoleUser.values()).noneMatch((roleUser -> roleUser.getValue().equals(data.getRole()))))
                throw new ApiException("Chức danh không hợp lệ", "password", HttpStatus.BAD_REQUEST);
        }

        boolean checkUser = userDAO.checkUser(data.getUsername());
        if (!checkUser) {
            throw new ApiException("Tên tài khoản đã tồn tại", "username", HttpStatus.BAD_REQUEST);
        }

        return userDAO.registerUser(data);
    }
}

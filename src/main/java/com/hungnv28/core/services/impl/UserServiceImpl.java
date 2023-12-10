package com.hungnv28.core.services.impl;

import com.hungnv28.core.daos.UserDAO;
import com.hungnv28.core.entities.Users;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.services.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public Users checkUser(String username, String password) throws Exception {
        if (StringUtils.isEmpty(username)) {
            throw new ApiException("Tên tài khoản không được trống", "username", HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isEmpty(password)) {
            throw new ApiException("Mật khẩu không được trống", "password", HttpStatus.BAD_REQUEST);
        }

        Users users = userDAO.checkUser(username, password);
        if (users == null) {
            throw new ApiException("Thông tin tài khoản hoặc mật khẩu không chính xác", "user_not_found", HttpStatus.NOT_FOUND);
        }

        return users;
    }
}

package com.hungnv28.core.services.impl;

import com.hungnv28.core.dtos.auth.SignUpRequestDTO;
import com.hungnv28.core.daos.UserDAO;
import com.hungnv28.core.dtos.user.UserInfoDTO;
import com.hungnv28.core.enums.RoleUser;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.models.UserInfo;
import com.hungnv28.core.services.UserService;
import com.hungnv28.core.utils.CommonUtils;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public UserInfoDTO loginUser(String username, String password) throws Exception {
        if (StringUtils.isEmpty(username)) {
            throw new ApiException("Tên tài khoản không được trống", "username");
        }

        if (StringUtils.isEmpty(password)) {
            throw new ApiException("Mật khẩu không được trống", "password");
        }

        UserInfoDTO users = userDAO.loginUser(username, password);
        if (users == null) {
            throw new ApiException("Thông tin tài khoản hoặc mật khẩu không chính xác", "user_not_found");
        }

        return users;
    }

    @Override
    public boolean checkUser(String username) throws Exception {
        if (StringUtils.isEmpty(username)) {
            throw new ApiException("Tên tài khoản không được trống", "username");
        }

        return userDAO.checkUser(username);
    }

    @Override
    public boolean registerUser(SignUpRequestDTO data) throws Exception {
        if (StringUtils.isEmpty(data.getUsername())) {
            throw new ApiException("Tên tài khoản không được trống", "username");
        }

        if (StringUtils.isEmpty(data.getPassword())) {
            throw new ApiException("Mật kẩu không được trống", "password");
        }

        if (StringUtils.isNotEmpty(data.getRole())) {
            if (Arrays.stream(RoleUser.values()).noneMatch((roleUser -> roleUser.getValue().equals(data.getRole()))))
                throw new ApiException("Chức danh không hợp lệ", "password");
        }

        boolean checkUser = userDAO.checkUser(data.getUsername());
        if (!checkUser) {
            throw new ApiException("Tên tài khoản đã tồn tại", "username");
        }

        return userDAO.registerUser(data);
    }

    @Override
    public UserInfoDTO findUserById(String id, UserInfo userInfo) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new ApiException("Id là bắt buộc", "user_id");
        }

        if (!CommonUtils.isNumber(id)) {
            throw new ApiException("Id phải là một số", "user_id");
        }

        int userId = Integer.parseInt(id);
        UserInfoDTO users = userDAO.findUserById(userId);

        if (users == null) {
            throw new ApiException("Không tìm thấy thông tin người dùng", "user_info");
        }

        if (userId != userInfo.getUserId() && userInfo.getRole().equals(RoleUser.USER.getValue())) {
            throw new ApiException("Bạn không thể thực hiện chức năng này", "error_403");
        }

        return users;
    }
}

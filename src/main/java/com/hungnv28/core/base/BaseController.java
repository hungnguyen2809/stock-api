package com.hungnv28.core.base;

import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.exception.CodeException;
import com.hungnv28.core.exception.ErrorResponse;
import com.hungnv28.core.models.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected UserInfo getCurrentUser() throws Exception {
        UserInfo info = null;
        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                info = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        } catch (Exception e) {
            logger.error("BaseController_getCurrentUser: {}", e.getMessage());
        }

        if (info == null || info.getUserId() == null || !info.verify()) {
            throw new ApiException("Phiên đăng nhập không hợp lệ. Vui lòng đăng nhập lại.", CodeException.TOKEN_EXPIRES.getValue(), HttpStatus.UNAUTHORIZED);
        }

        return info;
    }

    protected ResponseEntity<BaseResponse> successApi(Object data) {
        BaseResponse response = new BaseResponse();
        response.setError(false);
        response.setData(data);
        response.setMessage("OK");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<BaseResponse> successApi(Object data, String message) {
        BaseResponse response = new BaseResponse();
        response.setError(false);
        response.setData(data);
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<BaseResponse> successApi(Object data, String code, String message) {
        BaseResponse response = new BaseResponse();
        response.setError(false);
        response.setData(data);
        response.setCode(code);
        response.setMessage(StringUtils.isEmpty(message) ? "OK" : message);
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<BaseResponse> errorApi(String message) {
        BaseResponse response = new BaseResponse();
        response.setError(true);
        response.setMessage(message);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<BaseResponse> errorApi(String message, String code) {
        BaseResponse response = new BaseResponse();
        response.setError(true);
        response.setCode(code);
        response.setMessage(message);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<BaseResponse> errorApi(ErrorResponse error) {
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}

package com.hungnv28.core.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

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
        response.setMessage(message);
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

}

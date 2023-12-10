package com.hungnv28.core.exception;

import com.hungnv28.core.base.BaseResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponse extends BaseResponse {
    public ErrorResponse(ApiException exception) {
        setError(true);
        setCode(exception.getCode());
        setMessage(exception.getMessage());
        setStatus(exception.getStatus().value());
    }
}

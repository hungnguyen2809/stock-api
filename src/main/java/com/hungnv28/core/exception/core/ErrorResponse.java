package com.hungnv28.core.exception.core;

import com.hungnv28.core.base.BaseResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorResponse extends BaseResponse {
    public ErrorResponse(ApiException exception) {
        this.error = true;
        this.code = exception.getCode();
        this.message = exception.getMessage();
        this.status = exception.getStatus().value();
    }
}

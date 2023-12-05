package com.hungnv28.core.exception;

import com.hungnv28.core.exception.core.ApiException;
import com.hungnv28.core.exception.core.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorResponse> handle(ApiException exception) {
        ErrorResponse response = new ErrorResponse(exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }
}

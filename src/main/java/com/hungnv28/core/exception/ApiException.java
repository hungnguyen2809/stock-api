package com.hungnv28.core.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ApiException extends Exception {
    protected String code;
    protected String stack;
    protected String message;
    protected HttpStatus status;

    public ApiException(String message) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.code = CodeException.SERVER_ERROR.getValue();
    }

    public ApiException(String message, String code) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApiException(String message, String code, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}

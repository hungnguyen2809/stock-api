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
    protected String message;
    protected HttpStatus status;

    public ApiException(String message) {
        this.message = message;
        this.status = HttpStatus.OK;
    }

    public ApiException(String message, String code) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.OK;
    }

    public ApiException(String message, String code, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public ApiException(Exception exception) {
        this.status = HttpStatus.OK;
        this.message = exception.getMessage();

        if (exception instanceof ApiException) {
            this.code = ((ApiException) exception).getCode();
            this.status = ((ApiException) exception).getStatus();
        }
    }
}

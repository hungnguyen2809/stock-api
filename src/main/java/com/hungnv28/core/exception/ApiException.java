package com.hungnv28.core.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends Exception {
    protected String code;
    protected String stack;
    protected String message;
    protected HttpStatus status;

    public ApiException(String message) {
        this.message = message;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    }

    public ApiException(String message, String code) {
        this.code = code;
        this.message = message;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    }
}

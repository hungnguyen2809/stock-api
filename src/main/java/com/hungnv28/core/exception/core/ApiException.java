package com.hungnv28.core.exception.core;


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
}

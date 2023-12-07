package com.hungnv28.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeException {
    SERVER_ERROR("error_server"),
    TOKEN_EXPIRES("token_expires");

    private final String value;
}

package com.hungnv28.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleUser {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;
}

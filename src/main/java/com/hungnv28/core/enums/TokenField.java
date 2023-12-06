package com.hungnv28.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenField {
    iat("iat"),
    exp("exp"),
    token("token"),

    header_alg("alg"),
    header_typ("typ"),

    phone("phone"),
    email("email"),
    userId("user_id"),
    fullName("full_name"),
    userName("user_name");

    private final String value;


}

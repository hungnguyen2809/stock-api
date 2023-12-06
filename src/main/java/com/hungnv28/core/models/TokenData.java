package com.hungnv28.core.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TokenData implements Serializable {
    private Long userId;
    private String phone;
    private String email;
    private String userName;
    private String fullName;

    private Long iat;
    private Long exp;
}

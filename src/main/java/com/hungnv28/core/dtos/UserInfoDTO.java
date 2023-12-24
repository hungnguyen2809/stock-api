package com.hungnv28.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO implements Serializable {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private String country;
    private String dateOfBirth;
}

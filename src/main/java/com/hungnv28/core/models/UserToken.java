package com.hungnv28.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String role;
}

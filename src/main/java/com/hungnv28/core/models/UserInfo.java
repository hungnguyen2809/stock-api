package com.hungnv28.core.models;

import com.hungnv28.core.enums.TokenField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class UserInfo implements Serializable {
    private Map<String, Object> info;

    public UserInfo(Map<String, Object> info) {
        this.info = info;
    }

    public boolean verify() {
        long exp = (long) info.getOrDefault(TokenField.exp.getValue(), 0);
        return exp == 0 || exp > System.currentTimeMillis();
    }

    public Long getUserId() {
        return (Long) info.get(TokenField.userId.getValue());
    }

    public String getRole() {
        return (String) info.get(TokenField.role.getValue());
    }

    public String getUsername() {
        return (String) info.get(TokenField.userName.getValue());
    }

    public String getFullName() {
        return (String) info.get(TokenField.fullName.getValue());
    }

    public String getEmail() {
        return (String) info.get(TokenField.email.getValue());
    }

    public Long getPhone() {
        return (Long) info.get(TokenField.phone.getValue());
    }
}

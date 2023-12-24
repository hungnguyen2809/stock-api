package com.hungnv28.core.models;

import com.hungnv28.core.enums.TokenField;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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

    public String getPhone() {
        return (String) info.get(TokenField.phone.getValue());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (StringUtils.isEmpty(getRole())) return List.of();
        return List.of(new SimpleGrantedAuthority(getRole().toUpperCase()));
    }

}

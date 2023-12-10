package com.hungnv28.core.entities;

import com.hungnv28.core.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DATE_OF_BIRTH")
    private Timestamp dateOfBirth;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ROLE")
    private RoleUser role;
}

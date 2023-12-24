package com.hungnv28.core.daos.impl;

import com.hungnv28.core.base.BaseDAO;
import com.hungnv28.core.controllers.request.SignUpRequest;
import com.hungnv28.core.daos.UserDAO;
import com.hungnv28.core.dtos.UserInfoDTO;
import com.hungnv28.core.enums.RoleUser;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.utils.DateTimeFormatterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDAOImpl extends BaseDAO implements UserDAO {

    @Qualifier("coreFactory")
    private final SessionFactory sessionFactory;

    public UserInfoDTO loginUser(String username, String password) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("CALL ERD_STOCK.LOGIN_USER(:username, :password)", Object.class)
                    .setParameter("username", username)
                    .setParameter("password", password);

            List<Object[]> resultList = query.getResultList();
            List<UserInfoDTO> usersList = new ArrayList<>();

            for (Object[] object : resultList) {
                String dateOfBrith = DateTimeFormatterUtil.formatTimestamp((Timestamp) object[6], DateTimeFormatterUtil.DDMMYYYY);

                UserInfoDTO user = new UserInfoDTO();
                user.setUserId((Integer) object[0]);
                user.setUsername((String) object[1]);
                user.setFullName((String) object[3]);
                user.setEmail((String) object[4]);
                user.setPhone((String) object[5]);
                user.setDateOfBirth(dateOfBrith);
                user.setCountry((String) object[7]);
                user.setRole((String) object[8]);

                usersList.add(user);
            }

            return !usersList.isEmpty() ? usersList.get(0) : null;
        } catch (Exception e) {
            log.error("UserDAO_loginUser: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public boolean checkUser(String username) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("CALL ERD_STOCK.CHECK_USER(:username)", Object.class)
                    .setParameter("username", username);

            return query.getResultList().isEmpty();
        } catch (Exception e) {
            log.error("UserDAO_checkUser: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public boolean registerUser(SignUpRequest data) throws Exception {
        try {
            String role = StringUtils.isEmpty(data.getRole()) ? RoleUser.USER.getValue() : data.getRole();
            String dateOfBrith = DateTimeFormatterUtil.parseDate(data.getDateOfBrith(), DateTimeFormatterUtil.DDMMYYYY)
                    .format(DateTimeFormatterUtil.YYYYMMDD);

            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("CALL ERD_STOCK.REGISTER_USER(:username, :password, :fullName, :email, " +
                            ":phone, :dateOfBirth, :country, :role)", Object.class)
                    .setParameter("username", data.getUsername())
                    .setParameter("password", data.getPassword())
                    .setParameter("fullName", data.getFullName())
                    .setParameter("email", data.getEmail())
                    .setParameter("phone", data.getPhone())
                    .setParameter("dateOfBirth", dateOfBrith)
                    .setParameter("country", data.getCountry())
                    .setParameter("role", role);

            return !query.getResultList().isEmpty();
        } catch (Exception e) {
            log.error("UserDAO_registerUser: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public UserInfoDTO findUserById(Integer id) throws Exception {

        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("SELECT * FROM ERD_STOCK.USERS WHERE USER_ID = ?", Object.class)
                    .setParameter(1, id);

            List<Object[]> resultList = query.getResultList();
            List<UserInfoDTO> usersList = new ArrayList<>();

            for (Object[] object : resultList) {
                String dateOfBrith = DateTimeFormatterUtil.formatTimestamp((Timestamp) object[6], DateTimeFormatterUtil.DDMMYYYY);

                UserInfoDTO user = new UserInfoDTO();
                user.setUserId((Integer) object[0]);
                user.setUsername((String) object[1]);
                user.setFullName((String) object[3]);
                user.setEmail((String) object[4]);
                user.setPhone((String) object[5]);
                user.setDateOfBirth(dateOfBrith);
                user.setCountry((String) object[7]);
                user.setRole((String) object[8]);

                usersList.add(user);
            }

            return !usersList.isEmpty() ? usersList.get(0) : null;
        } catch (Exception e) {
            log.error("UserDAO_findUserById: {}", e.getMessage());
            throw new ApiException(e);
        }
    }
}

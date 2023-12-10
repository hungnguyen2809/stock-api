package com.hungnv28.core.daos.impl;

import com.hungnv28.core.base.BaseDAO;
import com.hungnv28.core.daos.UserDAO;
import com.hungnv28.core.entities.Users;
import com.hungnv28.core.enums.RoleUser;
import com.hungnv28.core.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDAOImpl extends BaseDAO implements UserDAO {

    @Qualifier("coreFactory")
    private final SessionFactory sessionFactory;


    public Users checkUser(String username, String password) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            NativeQuery query = session.createNativeQuery("CALL CHECK_LOGIN(:username, :password);", Object.class)
                    .setParameter("username", username)
                    .setParameter("password", password);

            List<Object[]> objectList = query.getResultList();
            List<Users> usersList = new ArrayList<>();

            for (Object[] object : objectList) {
                Users user = new Users();
                user.setUserId((Integer) object[0]);
                user.setUsername((String) object[1]);
                user.setFullName((String) object[3]);
                user.setEmail((String) object[4]);
                user.setPhone((String) object[5]);
                user.setDateOfBirth((Timestamp) object[6]);
                user.setCountry((String) object[7]);
                user.setRole(getRole((String) object[8]));

                usersList.add(user);
            }

            return !usersList.isEmpty() ? usersList.get(0) : null;

        } catch (Exception e) {
            log.error("UserDAO_checkUser: {}", e.getMessage());
            throw new ApiException(e.getMessage());
        } finally {
            commit(session, null);
        }
    }

    private RoleUser getRole(String role) {
        if (role.equals(RoleUser.ADMIN.getValue())) {
            return RoleUser.ADMIN;
        }
        return RoleUser.USER;
    }
}

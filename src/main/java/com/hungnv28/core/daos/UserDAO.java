package com.hungnv28.core.daos;

import com.hungnv28.core.base.BaseDAO;
import com.hungnv28.core.entities.Users;
import com.hungnv28.core.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserDAO extends BaseDAO {

    @Qualifier("coreFactory")
    SessionFactory sessionFactory;


    public Users checkUser(String username, String password) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery<Users> query = session.createNativeQuery("CALL CHECK_LOGIN(:username, :password);", Users.class)
                    .setParameter("username", username)
                    .setParameter("password", password);

            List<Users> objectList = query.list();

            log.info("objectList: {}", objectList);

            return null;
        } catch (Exception e) {
            log.error("UserDAO_checkUser: {}", e.getMessage());
            throw new ApiException(e.getMessage());
        }
    }
}

package com.hungnv28.core.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Transactional
public class BaseDao {

    protected Session getSession(SessionFactory sf) {
        return sf.getCurrentSession();
    }

    protected Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}

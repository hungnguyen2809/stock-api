package com.hungnv28.core.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class BaseDAO {

    protected Session getSession(SessionFactory sf) {
        return sf.getCurrentSession();
    }

    protected Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    public void commit(Session session, Transaction transaction) {
        try {
            if (transaction != null) {
                transaction.commit();
            }
        } catch (Exception e) {
            //TODO ignored
        }

        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception e) {
            //TODO ignored
        }
    }
}

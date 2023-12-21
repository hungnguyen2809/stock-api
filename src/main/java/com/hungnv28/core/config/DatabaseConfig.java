package com.hungnv28.core.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
public class DatabaseConfig {

    @Value("${sys.database.url}")
    String dbUrl;

    @Value("${sys.database.name}")
    String dbName;

    @Value("${sys.database.username}")
    String dbUsername;

    @Value("${sys.database.password}")
    String dbPassword;

    @Primary
    @Bean(name = "coreSource", destroyMethod = "close")
    public HikariDataSource getDataSource() throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setAutoCommit(true);
        dataSource.setMinimumIdle(8);
        dataSource.setMaximumPoolSize(16);
        dataSource.setLoginTimeout(1000);
        dataSource.setIdleTimeout(300000);
        dataSource.setMaxLifetime(600000);
        dataSource.setConnectionTimeout(30000);
        dataSource.setInitializationFailTimeout(1000);

        return dataSource;
    }

    @Autowired
    @Bean(name = "coreFactory")
    public SessionFactory sessionFactory(@Qualifier("coreSource") HikariDataSource dataSource) throws IOException {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setHibernateProperties(properties);
        factoryBean.setDataSource(dataSource);
        factoryBean.afterPropertiesSet();

        SessionFactory sessionFactory = factoryBean.getObject();
        log.info("DatabaseConfig_sessionFactory: {}", sessionFactory);
        return sessionFactory;
    }
}

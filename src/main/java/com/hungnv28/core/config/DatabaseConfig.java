package com.hungnv28.core.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class})
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
    public HikariDataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setAutoCommit(true);
        dataSource.setMinimumIdle(8);
        dataSource.setMaximumPoolSize(64);
        dataSource.setConnectionTimeout(30000);
        dataSource.setConnectionInitSql("SELECT 1 FROM DUAL");
        dataSource.setConnectionTestQuery("SELECT 1 FROM DUAL");

        return dataSource;
    }
}

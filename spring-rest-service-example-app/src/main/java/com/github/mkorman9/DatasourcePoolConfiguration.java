package com.github.mkorman9;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourcePoolConfiguration {
    @Value("${datasource.hikari.driverclassname}")
    private String driverClassName;

    @Value("${datasource.hikari.jdbc.url}")
    private String jdbcUrl;

    @Value("${datasource.hikari.username}")
    private String userName;

    @Value("${datasource.hikari.password}")
    private String password;

    @Value("${datasource.hikari.pool.maxLifetime}")
    private int maxLifetime;

    @Value("${datasource.hikari.pool.leakDetectionThreshold}")
    private int leakDetectionThreshold;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaxLifetime(maxLifetime);
        ds.setLeakDetectionThreshold(leakDetectionThreshold);
        ds.setDriverClassName(driverClassName);
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(userName);
        ds.setPassword(password);
        return ds;
    }
}

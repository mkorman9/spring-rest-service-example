package com.github.mkorman9;

import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class JpaConfiguration {
    @Value("${cache.address}")
    private String cacheAddress;

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("h4m.adapter.spymemcached.hosts", cacheAddress);

        Try.run(() -> properties.load(getClass().getResourceAsStream("/META-INF/hibernate.properties")))
                .onFailure(this::reportErrorWithLoadingProperties);

        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource source, Properties hibernateProperties) throws IOException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(source);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPackagesToScan("com.github.mkorman9.entity");
        factoryBean.setJpaProperties(hibernateProperties);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }

    private void reportErrorWithLoadingProperties(Throwable e) {
        throw new RuntimeException("Cannot load Hibernate properties file", e);
    }
}

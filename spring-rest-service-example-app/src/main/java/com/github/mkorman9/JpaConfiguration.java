package com.github.mkorman9;

import com.google.common.collect.ImmutableList;
import javaslang.control.Try;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
    @Value("${cache.address:null}")
    private String cacheAddress;
    @Autowired
    private Environment environment;

    @Bean
    public Properties hibernateProperties() {
        val properties = new Properties();
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");

        if (productionProfileActive()) {
            loadCacheRelatedProperties(properties);
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "none");
        }
        else {
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "create");
        }

        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource source, Properties hibernateProperties) throws IOException {
        val factoryBean = new LocalContainerEntityManagerFactoryBean();
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

    private boolean productionProfileActive() {
        return ImmutableList.copyOf(environment.getActiveProfiles()).contains("production");
    }

    private void loadCacheRelatedProperties(Properties properties) {
        properties.setProperty("cache.address", cacheAddress);
        Try.run(() -> properties.load(getClass().getResourceAsStream("/hibernate-cache.properties")))
                .onFailure(e -> {
                    throw new RuntimeException("Cannot load Hibernate properties file", e);
                });
    }
}

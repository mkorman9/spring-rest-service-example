package com.github.mkorman9;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class JpaConfiguration {
    @Bean
    public JndiObjectFactoryBean cacheServerAddress() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:comp/env/cacheServerAddress");
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setCache(true);
        jndiObjectFactoryBean.setExpectedType(String.class);
        return jndiObjectFactoryBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource source, String cacheAddress) {
        HibernateJpaVendorAdapter jpaVendorAdapter  = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(source);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("com.github.mkorman9.model");
        factoryBean.setJpaProperties(new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "validate");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

                // Cache config
                setProperty("hibernate.cache.use_second_level_cache", "true");
                setProperty("hibernate.cache.use_query_cache", "true");
                setProperty("hibernate.cache.default_cache_concurrency_strategy", "NONSTRICT_READ_WRITE");
                setProperty("hibernate.cache.region.factory_class", "kr.pe.kwonnam.hibernate4memcached.Hibernate4MemcachedRegionFactory");
                setProperty("hibernate.cache.region_prefix", "springtest");
                setProperty("hibernate.cache.provider_configuration_file_resource_path", "META-INF/h4m-properties.xml");
                setProperty("hibernate.cache.use_structured_entries", "false");
                setProperty("h4m.adapter.class", "kr.pe.kwonnam.hibernate4memcached.spymemcached.SpyMemcachedAdapter");
                setProperty("h4m.adapter.spymemcached.hosts", cacheAddress);
                setProperty("h4m.adapter.spymemcached.hashalgorithm", "KETAMA_HASH");
                setProperty("h4m.adapter.spymemcached.operation.timeout.millis", "5000");
                setProperty("h4m.adapter.spymemcached.transcoder", "kr.pe.kwonnam.hibernate4memcached.spymemcached.KryoTranscoder");
                setProperty("h4m.adapter.spymemcached.cachekey.prefix", "h4m");
                setProperty("h4m.adapter.spymemcached.kryotranscoder.compression.threashold.bytes", "20000");
            }
        });
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}

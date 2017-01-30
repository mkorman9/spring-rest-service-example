package com.github.mkorman9;

import com.github.mkorman9.web.filter.SampleFilter;
import lombok.val;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    public FilterRegistrationBean sampleFilterRegistration(SampleFilter sampleFilter) {
        val registration = new FilterRegistrationBean();
        registration.setFilter(sampleFilter);
        registration.addUrlPatterns("/*");  // it's impossible to exclude some urls. Secured URLs should be bound to different context than unsecured
        registration.setName("sampleFilter");
        registration.setOrder(1);
        return registration;
    }
}

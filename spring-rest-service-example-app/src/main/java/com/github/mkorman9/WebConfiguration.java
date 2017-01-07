package com.github.mkorman9;

import com.github.mkorman9.web.filter.SampleFilter;
import lombok.val;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    public SampleFilter sampleFilter() {
        return new SampleFilter();
    }

    @Bean
    public FilterRegistrationBean sampleFilterRegistartion(SampleFilter sampleFilter) {
        val registartation = new FilterRegistrationBean();
        registartation.setFilter(sampleFilter);
        registartation.addUrlPatterns("/*");
        registartation.setName("sampleFilter");
        registartation.setOrder(1);
        return registartation;
    }
}

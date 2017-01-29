package com.github.mkorman9;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
@EnableAutoConfiguration
@EnableEurekaClient
public class ClusterConfiguration {
}

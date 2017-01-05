package com.github.mkorman9;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static javaslang.API.*;

@Configuration
@EnableRabbit
public class MessagingConfiguration {
    public static final String QUEUE_NAME = "someSampleQueueForSpring";

    @Value("${broker.address}")
    private String brokerAddress;
    @Value("${broker.username}")
    private String brokerUsername;
    @Value("${broker.password}")
    private String brokerPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        String[] addressParts = parseBrokerAddress(brokerAddress);
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(addressParts[0], Integer.valueOf(addressParts[1]));
        connectionFactory.setUsername(brokerUsername);
        connectionFactory.setPassword(brokerPassword);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setRoutingKey(QUEUE_NAME);
        template.setQueue(QUEUE_NAME);
        return template;
    }

    @Bean
    public Queue queueDeclaration() {
        return new Queue(QUEUE_NAME, true);
    }

    private String[] parseBrokerAddress(String brokerAddress) {
        String[] parts = brokerAddress.split(":");
        return Match(parts.length).of(
                Case($(1), new String[] { parts[0], "5672" }),
                Case($(), parts)
        );
    }
}

package com.github.mkorman9.messaging;

import com.github.mkorman9.MessagingConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = MessagingConfiguration.QUEUE_NAME)
public class SampleListener {
    @RabbitHandler
    public void onMessage(String payload) {
        System.out.println("Received: " + payload);
    }
}

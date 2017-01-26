package com.github.mkorman9.messaging;

import com.github.mkorman9.MessagingConfiguration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SampleListener {
    @JmsListener(destination=MessagingConfiguration.QUEUE_NAME, containerFactory=MessagingConfiguration.CONTAINER_NAME)
    public void onMessage(String payload) {
        System.out.println("Received: " + payload);
    }
}

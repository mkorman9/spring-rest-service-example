package com.github.mkorman9.web.controller;

import com.github.mkorman9.MessagingConfiguration;
import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagingTestController {
    private JmsTemplate jmsTemplate;

    @Autowired
    public MessagingTestController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @RequestMapping(value = "/queue/send", method = RequestMethod.GET)
    public ResponseForm queueSend() {
        jmsTemplate.convertAndSend(MessagingConfiguration.QUEUE_NAME, "Hello world!");
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .data("ok")
                .build();
    }
}

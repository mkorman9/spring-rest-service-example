package com.github.mkorman9.web.controller;

import com.github.mkorman9.web.form.response.ResponseForm;
import com.github.mkorman9.web.form.response.ResponseStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagingTestController {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MessagingTestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(value = "/queue/send", method = RequestMethod.GET)
    public ResponseForm queueSend() {
        rabbitTemplate.convertAndSend("Hello world!");
        return ResponseForm.builder()
                .status(ResponseStatus.OK)
                .data("ok")
                .build();
    }
}

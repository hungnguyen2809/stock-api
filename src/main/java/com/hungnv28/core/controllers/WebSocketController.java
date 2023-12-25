package com.hungnv28.core.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping(value = "/chat")
    @SendTo(value = "/topic/messages")
    public String sendMessage(String message) {
        return message;
    }

}

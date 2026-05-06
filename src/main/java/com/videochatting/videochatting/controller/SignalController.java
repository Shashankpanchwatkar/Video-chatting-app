package com.videochatting.videochatting.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SignalController {

    private final SimpMessagingTemplate messagingTemplate;

    public SignalController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/offer/{roomId}")
    public void sendOffer(@DestinationVariable String roomId, String offer) {
        messagingTemplate.convertAndSend("/topic/offer/" + roomId, offer);
    }

    @MessageMapping("/answer/{roomId}")
    public void sendAnswer(@DestinationVariable String roomId, String answer) {
        messagingTemplate.convertAndSend("/topic/answer/" + roomId, answer);
    }

    @MessageMapping("/ice/{roomId}")
    public void sendIce(@DestinationVariable String roomId, String ice) {
        messagingTemplate.convertAndSend("/topic/ice/" + roomId, ice);
    }
}
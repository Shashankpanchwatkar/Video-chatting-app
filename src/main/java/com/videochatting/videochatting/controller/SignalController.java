package com.videochatting.videochatting.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.videochatting.videochatting.Service.RoomService;
import com.videochatting.videochatting.model.RoomMessage;

@Controller
public class SignalController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;

    public SignalController(
            SimpMessagingTemplate messagingTemplate,
            RoomService roomService
    ) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }

    @MessageMapping("/offer/{roomId}")
    public void sendOffer(
            @DestinationVariable String roomId,
            String offer
    ) {
        messagingTemplate.convertAndSend(
                "/topic/offer/" + roomId,
                offer
        );
    }

    @MessageMapping("/answer/{roomId}")
    public void sendAnswer(
            @DestinationVariable String roomId,
            String answer
    ) {
        messagingTemplate.convertAndSend(
                "/topic/answer/" + roomId,
                answer
        );
    }

    @MessageMapping("/ice/{roomId}")
    public void sendIce(
            @DestinationVariable String roomId,
            String ice
    ) {
        messagingTemplate.convertAndSend(
                "/topic/ice/" + roomId,
                ice
        );
    }

    @MessageMapping("/join")
    public void joinRoom(RoomMessage message) {

        roomService.joinRoom(
                message.getRoomId(),
                message.getUserId()
        );

        messagingTemplate.convertAndSend(
                "/topic/participants/" + message.getRoomId(),
                roomService.getParticipants(message.getRoomId())
        );
    }

    @MessageMapping("/leave")
    public void leaveRoom(RoomMessage message) {

        roomService.leaveRoom(
                message.getRoomId(),
                message.getUserId()
        );

        messagingTemplate.convertAndSend(
                "/topic/participants/" + message.getRoomId(),
                roomService.getParticipants(message.getRoomId())
        );
    }
}
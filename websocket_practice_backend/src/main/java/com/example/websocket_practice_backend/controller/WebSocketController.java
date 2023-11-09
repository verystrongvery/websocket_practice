package com.example.websocket_practice_backend.controller;

import com.example.websocket_practice_backend.constants.SendMessageType;
import com.example.websocket_practice_backend.domain.UserSubscription;
import com.example.websocket_practice_backend.domain.Users;
import com.example.websocket_practice_backend.dto.request.SendMessageRequestDto;
import com.example.websocket_practice_backend.dto.response.SendMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserSubscription userSubscription;

    @MessageMapping("/enter-chat-room/{chatRoomId}")
    public void handleEnterChatRoom(@DestinationVariable Long chatRoomId, Users users) {
        userSubscription.addSubscription(chatRoomId, users);
        SendMessageResponseDto sendMessageResponseDto = SendMessageResponseDto.from(users);
        List<Users> subscribedUsers = userSubscription.findSubscribedUsers(chatRoomId);
        sendMessageResponseDto.setSubscribedUsers(subscribedUsers);
        sendMessageResponseDto.setSendMessageType(SendMessageType.ENTER);
        simpMessagingTemplate.convertAndSend("/topic/" + chatRoomId, sendMessageResponseDto);
    }

    @MessageMapping("/exit-chat-room/{chatRoomId}")
    public void handleExitChatRoom(@DestinationVariable Long chatRoomId, Users users) {
        userSubscription.removeSubscription(chatRoomId, users.getId());
        SendMessageResponseDto sendMessageResponseDto = SendMessageResponseDto.from(users);
        List<Users> subscribedUsers = userSubscription.findSubscribedUsers(chatRoomId);
        sendMessageResponseDto.setSubscribedUsers(subscribedUsers);
        sendMessageResponseDto.setSendMessageType(SendMessageType.EXIT);
        simpMessagingTemplate.convertAndSend("/topic/" + chatRoomId, sendMessageResponseDto);
    }

    @MessageMapping("/send-message/{chatRoomId}")
    public void handleSendMessage(@DestinationVariable Long chatRoomId, SendMessageRequestDto sendMessageRequestDto) {
        SendMessageResponseDto sendMessageResponseDto = SendMessageResponseDto.from(sendMessageRequestDto);
        List<Users> subscribedUsers = userSubscription.findSubscribedUsers(chatRoomId);
        sendMessageResponseDto.setSubscribedUsers(subscribedUsers);
        sendMessageResponseDto.setSendMessageType(SendMessageType.TALK);
        simpMessagingTemplate.convertAndSend("/topic/" + chatRoomId, sendMessageResponseDto);
    }
}

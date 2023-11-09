package com.example.websocket_practice_backend.dto.response;

import com.example.websocket_practice_backend.constants.SendMessageType;
import com.example.websocket_practice_backend.domain.Users;
import com.example.websocket_practice_backend.dto.request.SendMessageRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class SendMessageResponseDto {
    private SendMessageType sendMessageType;
    private LocalDateTime time;
    private Users user;
    private String content;
    private List<Users> subscribedUsers;

    public static SendMessageResponseDto from(SendMessageRequestDto sendMessageRequestDto) {
        return SendMessageResponseDto.builder()
                .time(LocalDateTime.now())
                .user(sendMessageRequestDto.getUser())
                .content(sendMessageRequestDto.getContent())
                .build();
    }

    public static SendMessageResponseDto from(Users user) {
        return SendMessageResponseDto.builder()
                .time(LocalDateTime.now())
                .user(user)
                .build();
    }
}

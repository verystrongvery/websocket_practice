package com.example.websocket_practice_backend.dto.request;

import com.example.websocket_practice_backend.domain.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRequestDto {
    private Users user;
    private String content;
}

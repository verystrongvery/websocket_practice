package com.example.websocket_practice_backend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Users {
    private Long id;
    private String nickname;
    private String profileImage;
}

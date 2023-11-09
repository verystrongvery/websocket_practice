package com.example.websocket_practice_backend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
// WebSocketMessageBrokerConfigurer: 웹 소켓 연결을 구성하기 위한 메서드 제공
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    // 메시지 브로커: 송신자로부터 전달받은 메시지를 수신자로 전달해주는 중간 역할
    // MessageBrokerRegistry: 메시지 브로커를 구성하기 위한 메서드 제공
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커를 활성화
        config.enableSimpleBroker("/topic");
        // 메시지를 보낼 때 사용하는 경로의 prefix 설정
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    // 웹 소켓 엔드포인트 등록
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }
}

package com.boxinghub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // /topic dùng cho broadcast, /queue dùng cho chat riêng 1-1
        config.enableSimpleBroker("/topic", "/queue");
        // Prefix cho các message gửi từ client lên server
        config.setApplicationDestinationPrefixes("/app");
        // Prefix dành riêng cho tin nhắn cá nhân
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint để JS kết nối: new SockJS('/ws-chat')
        registry.addEndpoint("/ws-chat").withSockJS();
    }
}
package com.boxinghub.controller;

import com.boxinghub.dto.ChatMessageDTO;
import com.boxinghub.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO chatDTO) {
        // 1. Lưu tin nhắn vào Database thông qua Service
        chatService.saveMessage(chatDTO);

        // 2. Gửi tin nhắn đến người nhận qua đường dẫn riêng (/user/{email}/queue/messages)
        // Lưu ý: Receiver sẽ subscribe vào /user/queue/messages
        messagingTemplate.convertAndSendToUser(
                chatDTO.getReceiverEmail(),
                "/queue/messages",
                chatDTO
        );
    }
}
package com.boxinghub.service;

import com.boxinghub.dto.ChatMessageDTO;
import java.util.List;

public interface ChatService {
    void saveMessage(ChatMessageDTO chatDTO);

    List<ChatMessageDTO> getChatHistory(String email1, String email2);

    void markAsRead(String senderEmail, String receiverEmail);

    long getUnreadCount(String email);

    List<ChatMessageDTO> getContactedMembers(String email);
}
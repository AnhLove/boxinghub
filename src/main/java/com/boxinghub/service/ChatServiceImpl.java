package com.boxinghub.service.impl;

import com.boxinghub.dto.ChatMessageDTO;
import com.boxinghub.entity.ChatMessage;
import com.boxinghub.entity.Member;
import com.boxinghub.repository.ChatMessageRepository;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void saveMessage(ChatMessageDTO chatDTO) {
        Member sender = memberRepository.findByUserEmail(chatDTO.getSenderEmail())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Member receiver = memberRepository.findByUserEmail(chatDTO.getReceiverEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(chatDTO.getContent())
                .timestamp(LocalDateTime.now())
                .build();

        chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessageDTO> getChatHistory(String email1, String email2) {
        return chatMessageRepository.findChatHistory(email1, email2)
                .stream()
                .map(msg -> ChatMessageDTO.builder()
                        .content(msg.getContent())
                        .senderEmail(msg.getSender().getUser().getEmail())
                        .senderFullName(msg.getSender().getFullName())
                        .receiverEmail(msg.getReceiver().getUser().getEmail())
                        .timestamp(msg.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }
}
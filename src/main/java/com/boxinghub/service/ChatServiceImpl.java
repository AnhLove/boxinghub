package com.boxinghub.service;

import com.boxinghub.dto.ChatMessageDTO;
import com.boxinghub.entity.ChatMessage;
import com.boxinghub.entity.Member;
import com.boxinghub.repository.ChatMessageRepository;
import com.boxinghub.repository.MemberRepository;
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
                .read(false)
                .build();

        chatMessageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getChatHistory(String email1, String email2) {
        return chatMessageRepository.findChatHistory(email1, email2)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAsRead(String senderEmail, String receiverEmail) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMessages(senderEmail, receiverEmail);
        if (!unreadMessages.isEmpty()) {
            unreadMessages.forEach(msg -> msg.setRead(true));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(String email) {
        return chatMessageRepository.countByReceiverUserEmailAndReadFalse(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getContactedMembers(String email) {
        List<String> contactEmails = chatMessageRepository.findContactEmails(email);

        return contactEmails.stream()
                .map(contactEmail -> memberRepository.findByUserEmail(contactEmail).orElse(null))
                .filter(java.util.Objects::nonNull)
                .map(member -> ChatMessageDTO.builder()
                        .senderEmail(member.getUser().getEmail())
                        .senderFullName(member.getFullName())
                        .senderAvatar(member.getAvatarUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private ChatMessageDTO convertToDTO(ChatMessage msg) {
        return ChatMessageDTO.builder()
                .content(msg.getContent())
                .senderEmail(msg.getSender().getUser().getEmail())
                .senderFullName(msg.getSender().getFullName())
                .senderAvatar(msg.getSender().getAvatarUrl())
                .receiverEmail(msg.getReceiver().getUser().getEmail())
                .timestamp(msg.getTimestamp())
                .isRead(msg.isRead())
                .build();
    }
}
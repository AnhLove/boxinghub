package com.boxinghub.controller;

import com.boxinghub.dto.ChatMessageDTO;
import com.boxinghub.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/member/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    /**
     * Lấy lịch sử chat giữa người dùng hiện tại và một người khác.
     */
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDTO>> getHistory(
            @RequestParam String email,
            Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        String currentUserEmail = principal.getName();
        List<ChatMessageDTO> history = chatService.getChatHistory(currentUserEmail, email);
        return ResponseEntity.ok(history);
    }

    /**
     * API đếm tổng số tin nhắn chưa đọc để hiển thị badge (số đỏ) trên thanh Navigation.
     */
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(Principal principal) {
        if (principal == null) return ResponseEntity.ok(0L);
        return ResponseEntity.ok(chatService.getUnreadCount(principal.getName()));
    }

    /**
     * Lấy danh sách những người đã từng nhắn tin để hiển thị trong dropdown tin nhắn.
     */
    @GetMapping("/contacts")
    public ResponseEntity<List<ChatMessageDTO>> getContacts(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        // ChatService giờ trả về List<ChatMessageDTO> (chứa name, avatar, email)
        List<ChatMessageDTO> contacts = chatService.getContactedMembers(principal.getName());
        return ResponseEntity.ok(contacts);
    }

    /**
     * Đánh dấu các tin nhắn từ một người gửi cụ thể là "đã đọc" khi người dùng mở khung chat.
     */
    @PostMapping("/mark-as-read")
    public ResponseEntity<Void> markAsRead(@RequestParam String senderEmail, Principal principal) {
        if (principal == null) return ResponseEntity.status(401).build();

        chatService.markAsRead(senderEmail, principal.getName());
        return ResponseEntity.ok().build();
    }
}
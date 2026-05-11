package com.boxinghub.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDTO {
    private String content;           // Nội dung tin nhắn
    private String senderEmail;       // Dùng email làm định danh gửi (từ Principal)
    private String senderFullName;    // Hiển thị tên người gửi trên khung chat
    private String senderAvatar;      // Hiển thị ảnh đại diện người gửi
    private String receiverEmail;     // Email người nhận để server điều hướng tin nhắn
    private LocalDateTime timestamp;  // Thời gian gửi tin nhắn
    private boolean isRead;
}
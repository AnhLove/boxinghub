package com.boxinghub.repository;

import com.boxinghub.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Lấy lịch sử chat giữa người A và người B, sắp xếp theo thời gian tăng dần
    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(m.sender.user.email = :email1 AND m.receiver.user.email = :email2) OR " +
            "(m.sender.user.email = :email2 AND m.receiver.user.email = :email1) " +
            "ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(@Param("email1") String email1, @Param("email2") String email2);
}
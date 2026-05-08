package com.boxinghub.repository;

import com.boxinghub.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    // Lấy danh sách ticket của một Member cụ thể để hiện ở trang lịch sử báo lỗi
    List<SupportTicket> findByMemberIdOrderByCreatedAtDesc(Long memberId);
    List<SupportTicket> findAllByOrderByCreatedAtDesc();
}
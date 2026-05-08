package com.boxinghub.service;

import com.boxinghub.entity.SupportTicket;
import com.boxinghub.entity.TicketStatus;

import java.util.List;

public interface SupportService {
    SupportTicket createTicket(Long memberId, String title, String category, String content);
    List<SupportTicket> getMemberTickets(Long memberId);
    List<SupportTicket> getAllTickets();
    void updateTicketStatus(Long ticketId, TicketStatus status);
}

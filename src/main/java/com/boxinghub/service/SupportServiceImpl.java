package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.SupportTicket;
import com.boxinghub.entity.TicketStatus;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final SupportTicketRepository ticketRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public SupportTicket createTicket(Long memberId, String title, String category, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        SupportTicket ticket = new SupportTicket();
        ticket.setMember(member);
        ticket.setTitle(title);
        ticket.setCategory(category);
        ticket.setContent(content);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<SupportTicket> getMemberTickets(Long memberId) {
        return ticketRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
    }

    @Override
    public List<SupportTicket> getAllTickets() {
        return ticketRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional
    public void updateTicketStatus(Long ticketId, TicketStatus status) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ticket"));
        ticket.setStatus(status);
        ticketRepository.save(ticket);
    }
}

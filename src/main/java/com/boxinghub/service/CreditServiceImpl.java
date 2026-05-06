package com.boxinghub.service;

import com.boxinghub.entity.CreditTransaction;
import com.boxinghub.entity.Member;
import com.boxinghub.repository.CreditTransactionRepository;
import com.boxinghub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    // 1. Khai báo đúng tên biến Repository
    private final CreditTransactionRepository creditTransactionRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void addCredits(Long memberId, Integer amount, String note) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member không tồn tại"));

        // Cộng số buổi tập
        int currentSessions = member.getRemainingSessions() != null ? member.getRemainingSessions() : 0;
        member.setRemainingSessions(currentSessions + amount);
        memberRepository.save(member);

        // Lưu lịch sử
        CreditTransaction transaction = CreditTransaction.builder()
                .member(member)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .note(note)
                .build();

        creditTransactionRepository.save(transaction);
    }

    // 2. GIỮ LẠI HÀM NÀY (Có sử dụng EntityGraph để tránh lỗi Lazy loading)
    @Override
    public List<CreditTransaction> getAllTransactions() {
        return creditTransactionRepository.findAllByOrderByTransactionDateDesc();
    }

    @Override
    public List<CreditTransaction> getMemberTransactions(Long memberId) {
        // Lưu ý: Đảm bảo trong Repository đã có hàm findByMemberIdOrderByTransactionDateDesc
        return creditTransactionRepository.findByMemberIdOrderByTransactionDateDesc(memberId);
    }
}
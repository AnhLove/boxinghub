package com.boxinghub.service;

import com.boxinghub.entity.CreditTransaction;
import com.boxinghub.entity.Member;
import com.boxinghub.entity.TransactionStatus;
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

    private final CreditTransactionRepository creditTransactionRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void addCredits(Long memberId, Integer amount, String note) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member không tồn tại"));

        // 1. Cộng số buổi tập trực tiếp cho học viên
        int currentSessions = member.getRemainingSessions() != null ? member.getRemainingSessions() : 0;
        member.setRemainingSessions(currentSessions + amount);
        memberRepository.save(member);

        // 2. Lưu lịch sử giao dịch với trạng thái SUCCESS ngay lập tức (do Admin nạp tay)
        CreditTransaction transaction = CreditTransaction.builder()
                .member(member)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .note(note)
                .status(TransactionStatus.SUCCESS)
                .build();

        creditTransactionRepository.save(transaction);
    }

    @Override
    public List<CreditTransaction> getAllTransactions() {
        return creditTransactionRepository.findAllByOrderByTransactionDateDesc();
    }

    @Override
    public List<CreditTransaction> getMemberTransactions(Long memberId) {
        // Yêu cầu Repository có hàm: findByMemberIdOrderByTransactionDateDesc
        return creditTransactionRepository.findByMemberIdOrderByTransactionDateDesc(memberId);
    }

    @Override
    @Transactional
    public CreditTransaction createPaymentRequest(Long memberId, Integer sessions) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member không tồn tại"));

        // Tạo mã thanh toán duy nhất (Ví dụ: BH123456)
        String paymentCode = "BH" + (System.currentTimeMillis() % 1000000);

        double pricePerSession = 100000.0;
        double totalMoney = sessions * pricePerSession;

        CreditTransaction transaction = CreditTransaction.builder()
                .member(member)
                .amount(sessions)
                .moneyAmount(totalMoney)
                .transactionDate(LocalDateTime.now())
                .note("Nạp tiền qua Momo/Chuyển khoản")
                .status(TransactionStatus.PENDING)
                .paymentCode(paymentCode)
                .build();

        return creditTransactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void approveTransaction(Long transactionId) {
        CreditTransaction tx = creditTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Giao dịch không tồn tại"));

        // Kiểm tra xem giao dịch đã được xử lý chưa để tránh cộng buổi 2 lần
        if (tx.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Giao dịch này đã được xử lý trước đó (Thành công/Đã hủy)");
        }

        // 1. Cộng buổi tập cho Member
        Member member = tx.getMember();
        int current = (member.getRemainingSessions() != null) ? member.getRemainingSessions() : 0;
        member.setRemainingSessions(current + tx.getAmount());

        // 2. Cập nhật trạng thái giao dịch
        tx.setStatus(TransactionStatus.SUCCESS);
        tx.setTransactionDate(LocalDateTime.now()); // Lưu lại thời điểm duyệt thực tế

        memberRepository.save(member);
        creditTransactionRepository.save(tx);
    }
}
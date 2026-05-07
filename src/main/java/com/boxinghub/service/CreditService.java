package com.boxinghub.service;

import java.util.List;
import com.boxinghub.entity.CreditTransaction;

public interface CreditService {
    // Logic nạp buổi tập
    void addCredits(Long memberId, Integer amount, String note);

    // Lấy tất cả lịch sử giao dịch (cho Admin)
    List<CreditTransaction> getAllTransactions();

    // Lấy lịch sử theo từng Member
    List<CreditTransaction> getMemberTransactions(Long memberId);

    CreditTransaction createPaymentRequest(Long memberId, Integer sessions);
    void approveTransaction(Long transactionId);
}
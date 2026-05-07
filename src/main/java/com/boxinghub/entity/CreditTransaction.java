package com.boxinghub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer amount; // Số buổi nạp

    private Double moneyAmount; // Số tiền tương ứng (VD: 100k/buổi)

    private LocalDateTime transactionDate;

    private String note;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING, SUCCESS, CANCELLED

    @Column(unique = true)
    private String paymentCode; // Mã nội dung chuyển khoản duy nhất (VD: BH12345)
}
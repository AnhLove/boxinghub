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
    private Member member; // Nạp cho ai

    private Integer amount; // Số buổi nạp (ví dụ: +10, +20)

    private LocalDateTime transactionDate; // Ngày nạp

    private String note; // Ghi chú (ví dụ: "Nạp tại quầy", "Khuyến mãi")
}
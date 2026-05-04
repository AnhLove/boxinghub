package com.boxinghub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false)
    private String fullName; // Để hiển thị "Xin chào, Nguyễn Văn A" trên Topbar

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ROLE_ADMIN, ROLE_MEMBER, ROLE_TRAINER

    private boolean isActive = true; // Mặc định là true cho Member, nhưng có thể dùng để Admin duyệt Trainer
}
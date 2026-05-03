package com.boxinghub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity {

    // Liên kết 1-1 với tài khoản User
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    // Xóa email ở đây vì nó đã nằm trong bảng User rồi

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Double weight;
    private Double height;

    // Thêm các thông tin đặc thù cho Member
    private Integer remainingSessions = 0; // Số buổi tập còn lại
}
package com.boxinghub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "members")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Member extends BaseEntity {

    // Liên kết 1-1 với tài khoản User
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user = new User();

    @Column(name = "full_name", nullable = false)
    private String fullName;


    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Double weight;
    private Double height;

    // Thêm các thông tin đặc thù cho Member
    @Column(name = "remaining_sessions")
    private Integer remainingSessions = 0; // Số buổi tập còn lại
    @ManyToMany
    @JoinTable(
            name = "member_classes", // Tên bảng trung gian sẽ tự tạo trong DB
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<GroupClass> enrolledClasses = new java.util.ArrayList<>();
}
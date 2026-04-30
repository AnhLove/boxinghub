package com.boxinghub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "group_classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupClass extends BaseEntity {

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer; // Trainer phụ trách lớp này

    @Column(name = "schedule")
    private LocalDateTime schedule; // Lịch học

    @Column(name = "duration_minutes")
    private Integer durationMinutes; // Thời lượng (phút)

    @Column(name = "capacity")
    private Integer capacity; // Sức chứa tối đa

    @Column(name = "current_enrolled")
    private Integer currentEnrolled = 0; // Số người đã đăng ký

    @Column(name = "price")
    private Double price; // Giá 1 buổi

    @Enumerated(EnumType.STRING)
    private ClassStatus status; // OPEN, FULL, CANCELLED
}
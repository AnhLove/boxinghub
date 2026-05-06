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
    @Column(name = "status", length = 20)
    private ClassStatus status; // OPEN, FULL, CANCELLED

    public ClassStatus getStatus() {
        // 1. Ưu tiên cao nhất: Lệnh của Admin (CANCELLED)
        if (this.status == ClassStatus.CANCELLED) return ClassStatus.CANCELLED;

        // 2. Kiểm tra thời gian thực tế để đóng lớp (Luôn tính theo giờ hiện tại)
        if (this.schedule != null) {
            LocalDateTime now = LocalDateTime.now();
            int duration = (this.durationMinutes != null) ? this.durationMinutes : 120;
            LocalDateTime endTime = this.schedule.plusMinutes(duration);

            if (now.isAfter(endTime)) {
                return ClassStatus.CLOSED; // Trả về trạng thái đóng ảo để hiển thị
            }
        }

        // 3. Nếu chưa hết giờ, kiểm tra số lượng chỗ
        if (this.currentEnrolled != null && this.capacity != null
                && this.currentEnrolled >= this.capacity) {
            return ClassStatus.FULL;
        }

        // 4. Mặc định trả về giá trị Admin đã thiết lập (thường là OPEN)
        return this.status;
    }
}
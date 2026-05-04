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

//    public ClassStatus getStatus() {
//        // 1. Nếu Admin đã chủ động Hủy (CANCELLED) thì giữ nguyên
//        if (this.status == ClassStatus.CANCELLED) {
//            return ClassStatus.CANCELLED;
//        }
//
//        // 2. Nếu đã đủ người thì báo FULL
//        if (this.currentEnrolled != null && this.capacity != null
//                && this.currentEnrolled >= this.capacity) {
//            return ClassStatus.FULL;
//        }
//
//        // 3. Logic quan trọng: Kiểm tra thời gian để đóng lớp (CLOSED)
//        if (this.schedule != null) {
//            LocalDateTime now = LocalDateTime.now();
//
//            // Lấy duration từ field durationMinutes của bạn, nếu null thì mặc định 120
//            int duration = (this.durationMinutes != null) ? this.durationMinutes : 120;
//
//            // Thời điểm kết thúc = giờ bắt đầu + duration
//            LocalDateTime endTime = this.schedule.plusMinutes(duration);
//
//            // Nếu giờ hiện tại ĐÃ VƯỢT QUA giờ kết thúc thì mới hiện CLOSED
//            if (now.isAfter(endTime)) {
//                return ClassStatus.CLOSED;
//                // Lưu ý: ClassStatus của bạn phải có enum CLOSED, nếu chưa có hãy thêm vào file ClassStatus.java
//            }
//        }
//
//        // 4. Nếu chưa hết giờ và chưa đầy chỗ thì trả về giá trị trong DB (thường là OPEN)
//        return this.status;
//    }
    public ClassStatus getStatus() {
        // 1. Nếu Admin chủ động Hủy thì giữ nguyên
        if (this.status == ClassStatus.CANCELLED) return ClassStatus.CANCELLED;

        // 2. Nếu đã đầy chỗ thì báo FULL
        if (this.currentEnrolled != null && this.capacity != null
                && this.currentEnrolled >= this.capacity) {
            return ClassStatus.FULL;
        }

        // 3. Kiểm tra thời gian kết thúc (Schedule + Duration) mới đóng
        if (this.schedule != null) {
            LocalDateTime now = LocalDateTime.now();
            int duration = (this.durationMinutes != null) ? this.durationMinutes : 120;
            LocalDateTime endTime = this.schedule.plusMinutes(duration);

            // CHỈ ĐÓNG KHI ĐÃ QUA GIỜ KẾ THÚC
            if (now.isAfter(endTime)) {
                return ClassStatus.CLOSED;
            }
        }

        // 4. Nếu chưa tới giờ kết thúc, hãy trả về giá trị Admin đã lưu trong DB (OPEN/FULL/...)
        return this.status;
    }
}
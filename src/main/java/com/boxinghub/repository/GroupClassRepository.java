package com.boxinghub.repository;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface GroupClassRepository extends JpaRepository<GroupClass, Long> {
    List<GroupClass> findByStatus(ClassStatus status);
    List<GroupClass> findByTrainerId(Long trainerId);
    List<GroupClass> findByClassNameContainingIgnoreCase(String keyword);

    // Lọc lớp học trong khoảng thời gian (Dùng cho Upcoming Classes)
    List<GroupClass> findByScheduleBetweenOrderByScheduleAsc(LocalDateTime start, LocalDateTime end);
    // Tìm các lớp có trạng thái nhất định và thời gian bắt đầu trước một mốc cụ thể
    List<GroupClass> findByStatusAndScheduleBefore(ClassStatus status, LocalDateTime dateTime);
    // Thêm vào file GroupClassRepository.java
    List<GroupClass> findByScheduleAfterOrderByScheduleAsc(LocalDateTime time);
}
package com.boxinghub.service;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import java.util.List;
import java.util.Optional;

public interface GroupClassService {

    List<GroupClass> getAllGroupClasses();

    Optional<GroupClass> getGroupClassById(Long id);

    GroupClass saveGroupClass(GroupClass groupClass);

    void deleteGroupClass(Long id);

    List<GroupClass> findByStatus(ClassStatus status);

    List<GroupClass> findByTrainerId(Long trainerId);

    List<GroupClass> findByClassNameContainingIgnoreCase(String keyword);

    List<GroupClass> getOpenClasses();

    // Lấy các lớp sắp tới (trong 7 ngày tới)
    List<GroupClass> getUpcomingClasses();

    // Lấy các lớp đã qua (trong 30 ngày trước)
    List<GroupClass> getPastClasses();

    // Kiểm tra và cập nhật trạng thái lớp (FULL nếu currentEnrolled >= capacity)
    void updateClassStatus(Long classId);

    // Đăng ký học viên vào lớp
    boolean enrollMember(Long classId, Long memberId);

    // Hủy đăng ký
    boolean cancelEnrollment(Long classId, Long memberId);
}
package com.boxinghub.service;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import java.util.List;
import java.util.Optional;

public interface GroupClassService {
    List<GroupClass> getAllGroupClasses();
    Optional<GroupClass> getGroupClassById(Long id);
    List<GroupClass> findByStatus(ClassStatus status);
    List<GroupClass> findByTrainerId(Long trainerId);
    List<GroupClass> findByClassName(String keyword);

    // Lưu và tự động cập nhật trạng thái OPEN/FULL
    GroupClass saveGroupClass(GroupClass groupClass);
    void deleteGroupClass(Long id);

    // Lấy các lớp trong 7 ngày tới (Lọc từ DB)
    List<GroupClass> getUpcomingClasses();

    // Đăng ký và hủy đăng ký
    boolean enrollMember(Long classId, Long memberId);
    boolean cancelEnrollment(Long classId, Long memberId);
}
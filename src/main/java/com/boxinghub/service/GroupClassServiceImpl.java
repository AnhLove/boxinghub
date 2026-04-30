package com.boxinghub.service;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import com.boxinghub.repository.GroupClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GroupClassServiceImpl implements GroupClassService {

    @Autowired
    private GroupClassRepository groupClassRepository;

    @Override
    public List<GroupClass> getAllGroupClasses() {
        return groupClassRepository.findAll();
    }

    @Override
    public Optional<GroupClass> getGroupClassById(Long id) {
        return groupClassRepository.findById(id);
    }

    @Override
    public GroupClass saveGroupClass(GroupClass groupClass) {
        return groupClassRepository.save(groupClass);
    }

    @Override
    public void deleteGroupClass(Long id) {
        groupClassRepository.deleteById(id);
    }

    @Override
    public List<GroupClass> findByStatus(ClassStatus status) {
        return groupClassRepository.findByStatus(status);
    }

    @Override
    public List<GroupClass> findByTrainerId(Long trainerId) {
        return groupClassRepository.findByTrainerId(trainerId);
    }

    @Override
    public List<GroupClass> findByClassNameContainingIgnoreCase(String keyword) {
        return groupClassRepository.findByClassNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<GroupClass> getOpenClasses() {
        return findByStatus(ClassStatus.OPEN);
    }

    @Override
    public List<GroupClass> getUpcomingClasses() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusDays(7);
        return groupClassRepository.findAll().stream()
                .filter(c -> c.getSchedule() != null &&
                        c.getSchedule().isAfter(now) &&
                        c.getSchedule().isBefore(nextWeek))
                .toList();
    }

    @Override
    public List<GroupClass> getPastClasses() {
        LocalDateTime now = LocalDateTime.now();
        return groupClassRepository.findAll().stream()
                .filter(c -> c.getSchedule() != null && c.getSchedule().isBefore(now))
                .toList();
    }

    // --- Nhóm 4: Nghiệp vụ Đăng ký & Trạng thái ---

    @Override
    public void updateClassStatus(Long classId) {
        groupClassRepository.findById(classId).ifPresent(groupClass -> {
            if (groupClass.getCurrentEnrolled() >= groupClass.getCapacity()) {
                groupClass.setStatus(ClassStatus.FULL);
            } else if (groupClass.getCurrentEnrolled() < groupClass.getCapacity()) {
                groupClass.setStatus(ClassStatus.OPEN);
            }
            groupClassRepository.save(groupClass);
        });
    }

    @Override
    public boolean enrollMember(Long classId, Long memberId) {
        GroupClass groupClass = groupClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (groupClass.getStatus() == ClassStatus.OPEN &&
                groupClass.getCurrentEnrolled() < groupClass.getCapacity()) {

            groupClass.setCurrentEnrolled(groupClass.getCurrentEnrolled() + 1);

            // Tự động cập nhật FULL nếu đủ người
            if (groupClass.getCurrentEnrolled() >= groupClass.getCapacity()) {
                groupClass.setStatus(ClassStatus.FULL);
            }

            groupClassRepository.save(groupClass);
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelEnrollment(Long classId, Long memberId) {
        GroupClass groupClass = groupClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (groupClass.getCurrentEnrolled() > 0) {
            groupClass.setCurrentEnrolled(groupClass.getCurrentEnrolled() - 1);

            // Nếu đang FULL mà có người hủy thì mở lại lớp
            if (groupClass.getStatus() == ClassStatus.FULL) {
                groupClass.setStatus(ClassStatus.OPEN);
            }

            groupClassRepository.save(groupClass);
            return true;
        }
        return false;
    }
}
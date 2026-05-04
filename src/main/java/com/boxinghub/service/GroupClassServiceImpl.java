package com.boxinghub.service;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import com.boxinghub.repository.GroupClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupClassServiceImpl implements GroupClassService {

    private final GroupClassRepository groupClassRepository;

    @Override
    public List<GroupClass> getAllGroupClasses() {
        return groupClassRepository.findAll();
    }

    @Override
    public Optional<GroupClass> getGroupClassById(Long id) {
        return groupClassRepository.findById(id);
    }

    @Override
    @Transactional
    public GroupClass saveGroupClass(GroupClass groupClass) {
        if (groupClass.getCurrentEnrolled() == null) groupClass.setCurrentEnrolled(0);
        // Đảm bảo có sức chứa mặc định nếu admin quên nhập
        if (groupClass.getCapacity() == null) groupClass.setCapacity(30);

        LocalDateTime now = LocalDateTime.now();
        int duration = (groupClass.getDurationMinutes() != null) ? groupClass.getDurationMinutes() : 120;

        if (groupClass.getSchedule() != null) {
            LocalDateTime endTime = groupClass.getSchedule().plusMinutes(duration);

            if (now.isAfter(endTime)) {
                groupClass.setStatus(ClassStatus.CLOSED);
            } else if (groupClass.getCurrentEnrolled() >= groupClass.getCapacity()) {
                groupClass.setStatus(ClassStatus.FULL);
            } else if (groupClass.getStatus() != ClassStatus.CANCELLED) {
                groupClass.setStatus(ClassStatus.OPEN);
            }
        }
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
    public List<GroupClass> findByClassName(String keyword) {
        // Nếu không có từ khóa, phải hiện TẤT CẢ lớp
        if (keyword == null || keyword.trim().isEmpty()) {
            return groupClassRepository.findAll();
        }
        return groupClassRepository.findByClassNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public List<GroupClass> getUpcomingClasses() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusDays(7);
        return groupClassRepository.findByScheduleBetweenOrderByScheduleAsc(now, nextWeek);
    }

    @Override
    @Transactional
    public boolean enrollMember(Long classId, Long memberId) {
        return groupClassRepository.findById(classId).map(groupClass -> {
            if (groupClass.getStatus() == ClassStatus.OPEN &&
                    groupClass.getCurrentEnrolled() < groupClass.getCapacity()) {
                groupClass.setCurrentEnrolled(groupClass.getCurrentEnrolled() + 1);
                this.saveGroupClass(groupClass);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    @Transactional
    public boolean cancelEnrollment(Long classId, Long memberId) {
        return groupClassRepository.findById(classId).map(groupClass -> {
            if (groupClass.getCurrentEnrolled() > 0) {
                groupClass.setCurrentEnrolled(groupClass.getCurrentEnrolled() - 1);
                this.saveGroupClass(groupClass);
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public List<GroupClass> findAllAvailableForMembers() {
        LocalDateTime now = LocalDateTime.now();
        // Thay vì chỉ lấy OPEN, hãy lấy cả những lớp chưa kết thúc
        // Và việc lọc OPEN/CLOSED/FULL sẽ do hàm getStatus() ở Entity lo khi hiển thị ở giao diện

        // Lấy những lớp có giờ bắt đầu + 120 phút vẫn lớn hơn thời gian hiện tại
        LocalDateTime limitTime = now.minusMinutes(120);
        return groupClassRepository.findByScheduleAfterOrderByScheduleAsc(limitTime);
    }
}
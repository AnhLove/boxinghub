package com.boxinghub.service;

import com.boxinghub.entity.ClassStatus;
import com.boxinghub.entity.GroupClass;
import com.boxinghub.repository.GroupClassRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ClassStatusScheduler {

    private final GroupClassRepository groupClassRepository;

    public ClassStatusScheduler(GroupClassRepository groupClassRepository) {
        this.groupClassRepository = groupClassRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void autoCloseExpiredClasses() {
        LocalDateTime now = LocalDateTime.now();
        // Tìm các lớp OPEN hoặc FULL để kiểm tra đóng
        List<GroupClass> activeClasses = groupClassRepository.findAll();

        for (GroupClass gc : activeClasses) {
            int duration = (gc.getDurationMinutes() != null) ? gc.getDurationMinutes() : 120;
            LocalDateTime endTime = gc.getSchedule().plusMinutes(duration);

            // Chỉ đóng khi ĐÃ QUA GIỜ KẾ THÚC
            if (now.isAfter(endTime) && gc.getStatus() != ClassStatus.CLOSED) {
                gc.setStatus(ClassStatus.CLOSED);
                groupClassRepository.save(gc);
            }
        }
    }
}

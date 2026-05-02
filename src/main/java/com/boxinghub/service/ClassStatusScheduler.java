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

    // cron = "0 * * * * *" nghĩa là chạy vào giây thứ 0 của mỗi phút
    @Scheduled(cron = "0 * * * * *")
    public void autoCloseExpiredClasses() {
        LocalDateTime now = LocalDateTime.now();

        // Lấy danh sách các lớp đang OPEN nhưng đã quá giờ (như trường hợp Boxing1 trong ảnh của bạn)
        List<GroupClass> expiredClasses = groupClassRepository
                .findByStatusAndScheduleBefore(ClassStatus.OPEN, now);

        if (!expiredClasses.isEmpty()) {
            for (GroupClass gClass : expiredClasses) {
                gClass.setStatus(ClassStatus.CLOSED); // Chuyển sang CLOSED
            }
            groupClassRepository.saveAll(expiredClasses);

            // In ra console để bạn dễ theo dõi trong quá trình dev
            System.out.println("LOG: Đã tự động đóng " + expiredClasses.size() + " lớp học quá hạn lúc " + now);
        }
    }
}

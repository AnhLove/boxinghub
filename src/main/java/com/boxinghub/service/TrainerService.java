package com.boxinghub.service;

import com.boxinghub.entity.Trainer;
import com.boxinghub.entity.TrainerStatus;
import java.util.List;
import java.util.Optional;

public interface TrainerService {

    // Lấy tất cả trainer
    List<Trainer> getAllTrainers();

    // Tìm theo id
    Optional<Trainer> getTrainerById(Long id);

    // Thêm hoặc sửa trainer
    Trainer saveTrainer(Trainer trainer);

    // Xóa trainer
    void deleteTrainer(Long id);

    // Tìm theo tên
    List<Trainer> searchByName(String keyword);

    // Lấy danh sách trainer đang hoạt động
    List<Trainer> getActiveTrainers();
}
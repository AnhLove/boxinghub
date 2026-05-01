package com.boxinghub.service;

import com.boxinghub.entity.Trainer;
import com.boxinghub.entity.TrainerStatus;
import com.boxinghub.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Optional<Trainer> getTrainerById(Long id) {
        return trainerRepository.findById(id);
    }

    @Override
    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public List<Trainer> searchByName(String keyword) {
        // Nếu keyword null hoặc rỗng, trả về toàn bộ danh sách HLV
        if (keyword == null || keyword.trim().isEmpty()) {
            return trainerRepository.findAll();
        }
        // Lọc theo tên
        return trainerRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public List<Trainer> getActiveTrainers() {
        return trainerRepository.findByStatus(TrainerStatus.ACTIVE);
    }
}
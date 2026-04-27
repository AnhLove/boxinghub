package com.boxinghub.repository;

import com.boxinghub.entity.Trainer;
import com.boxinghub.entity.TrainerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByEmail(String email);

    List<Trainer> findByStatus(TrainerStatus status);

    List<Trainer> findByFullNameContainingIgnoreCase(String keyword);
}
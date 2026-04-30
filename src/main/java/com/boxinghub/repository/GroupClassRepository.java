package com.boxinghub.repository;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupClassRepository extends JpaRepository<GroupClass, Long> {

    List<GroupClass> findByStatus(ClassStatus status);

    List<GroupClass> findByTrainerId(Long trainerId);

    List<GroupClass> findByClassNameContainingIgnoreCase(String keyword);
}
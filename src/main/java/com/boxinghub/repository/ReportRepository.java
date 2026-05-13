package com.boxinghub.repository;

import com.boxinghub.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // ReportRepository.java
    @Query("SELECT r FROM Report r " +
            "LEFT JOIN FETCH r.post p " +
            "LEFT JOIN FETCH p.author " +
            "LEFT JOIN FETCH r.comment c " +
            "LEFT JOIN FETCH c.author " +
            "LEFT JOIN FETCH r.reporter " +
            "ORDER BY r.createdAt DESC")
    List<Report> findAllWithDetails();
    List<Report> findAllByOrderByCreatedAtDesc();
    long countByStatus(Report.ReportStatus status);
    List<Report> findByPostId(Long postId);
    List<Report> findByCommentId(Long commentId);
}
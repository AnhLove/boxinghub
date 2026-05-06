package com.boxinghub.repository;

import com.boxinghub.entity.CreditTransaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<CreditTransaction> findAllByOrderByTransactionDateDesc();

    @EntityGraph(attributePaths = {"member"})
    List<CreditTransaction> findByMemberIdOrderByTransactionDateDesc(Long memberId);
}
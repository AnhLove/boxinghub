package com.boxinghub.repository;

import com.boxinghub.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Tìm member theo email
    Optional<Member> findByEmail(String email);

    // Tìm member theo tên (không phân biệt hoa thường)
    java.util.List<Member> findByFullNameContainingIgnoreCase(String keyword);
}
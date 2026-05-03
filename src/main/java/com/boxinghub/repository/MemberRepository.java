package com.boxinghub.repository;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 1. Tìm hồ sơ member dựa trên đối tượng User (Dùng khi đã có User từ SecurityContext)
    Optional<Member> findByUser(User user);

    // 2. Tìm hồ sơ member dựa trên email của User (Spring Data JPA tự động Join sang bảng User)
    Optional<Member> findByUserEmail(String email);

    // 3. Tìm member theo tên để phục vụ chức năng tìm kiếm của Admin
    List<Member> findByFullNameContainingIgnoreCase(String keyword);

    // 4. Kiểm tra xem một User đã có hồ sơ Member chưa
    boolean existsByUser(User user);
}
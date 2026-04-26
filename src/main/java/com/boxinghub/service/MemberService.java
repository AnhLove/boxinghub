package com.boxinghub.service;

import com.boxinghub.entity.Member;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    // Lấy tất cả member
    List<Member> getAllMembers();

    // Tìm theo id
    Optional<Member> getMemberById(Long id);

    // Thêm hoặc sửa member
    Member saveMember(Member member);

    // Xóa member
    void deleteMember(Long id);

    // Tìm theo tên
    List<Member> searchByName(String keyword);
}
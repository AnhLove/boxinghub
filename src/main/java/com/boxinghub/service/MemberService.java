package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import java.util.List;
import java.util.Optional;

public interface MemberService {

    // --- Các hàm dành cho Admin quản lý ---
    List<Member> getAllMembers();
    Optional<Member> getMemberById(Long id);
    Member saveMember(Member member);
    void deleteMember(Long id);
    List<Member> searchByName(String keyword);

    // --- Các hàm dành cho luồng Member tự tương tác ---

    // Tìm hồ sơ dựa trên email đăng nhập (Quan trọng nhất)
    Optional<Member> getMemberByEmail(String email);

    // Kiểm tra xem một User đã có hồ sơ Member chưa
    // (Giúp phân biệt Member với Trainer/Admin khi login)
    boolean hasProfile(User user);

    // Đăng ký mới một Member (Bao gồm cả việc tạo User và hồ sơ Member)
    Member registerNewMember(Member member, User user);
}
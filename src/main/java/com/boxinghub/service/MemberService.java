package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import org.springframework.web.multipart.MultipartFile;

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

    // Tìm hồ sơ dựa trên email đăng nhập
    Optional<Member> getMemberByEmail(String email);

    boolean hasProfile(User user);

    Member registerNewMember(Member member, User user);

    void enrollInClass(Long memberId, Long classId);
    void cancelEnrollment(Long memberId, Long classId);
    void addSessions(Long memberId, int amount);
    void updateProfile(String email, Member profileData, MultipartFile avatarFile);
}
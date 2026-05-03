package com.boxinghub.service;

import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import com.boxinghub.repository.MemberRepository;
import com.boxinghub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository; // Thêm Repository để lưu User
    private final PasswordEncoder passwordEncoder; // Thêm Encoder để mã hóa mật khẩu

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    @Transactional
    public Member saveMember(Member member) {
        User user = member.getUser();

        // 1. Xử lý logic cho User đi kèm
        if (user != null) {
            if (user.getId() == null) {
                // Nếu là Member mới hoàn toàn
                user.setFullName(member.getFullName());
                user.setRole("ROLE_MEMBER");
                user.setPassword(passwordEncoder.encode("123456")); // Mật khẩu mặc định
                user.setActive(true);
                // Lưu User trước để có ID gắn vào Member
                userRepository.save(user);
            } else {
                // Nếu là cập nhật, đồng bộ lại tên nếu cần
                user.setFullName(member.getFullName());
                userRepository.save(user);
            }
        }

        // 2. Lưu Member
        return memberRepository.save(member);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public List<Member> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return memberRepository.findAll();
        }
        return memberRepository.findByFullNameContainingIgnoreCase(keyword);
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByUserEmail(email);
    }

    @Override
    public boolean hasProfile(User user) {
        return memberRepository.existsByUser(user);
    }

    @Override
    @Transactional
    public Member registerNewMember(Member member, User user) {
        // Mã hóa mật khẩu khi member tự đăng ký
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRole("ROLE_MEMBER");
        user.setActive(true);
        userRepository.save(user);

        member.setUser(user);
        return memberRepository.save(member);
    }
}
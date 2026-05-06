package com.boxinghub.service;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.Member;
import com.boxinghub.entity.User;
import com.boxinghub.repository.GroupClassRepository;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupClassRepository groupClassRepository;

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
        if (user != null) {
            if (user.getId() == null) {
                user.setFullName(member.getFullName());
                user.setRole("ROLE_MEMBER");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setActive(true);
                userRepository.save(user);
            } else {
                user.setFullName(member.getFullName());
                userRepository.save(user);
            }
        }
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
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRole("ROLE_MEMBER");
        user.setActive(true);
        userRepository.save(user);
        member.setUser(user);
        return memberRepository.save(member);
    }

    // --- LOGIC QUAN TRỌNG: ĐĂNG KÝ LỚP ---
    @Override
    @Transactional
    public void enrollInClass(Long memberId, Long classId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));
        GroupClass groupClass = groupClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        // 1. Kiểm tra nếu đã đăng ký rồi
        if (member.getEnrolledClasses().contains(groupClass)) {
            throw new RuntimeException("Bạn đã đăng ký lớp này rồi!");
        }

        // 2. Kiểm tra số buổi tập còn lại
        if (member.getRemainingSessions() == null || member.getRemainingSessions() <= 0) {
            throw new RuntimeException("Bạn đã hết buổi tập. Vui lòng nạp thêm!");
        }

        // 3. Kiểm tra sĩ số lớp (Capacity)
        if (groupClass.getCurrentEnrolled() >= groupClass.getCapacity()) {
            throw new RuntimeException("Lớp học đã đầy chỗ!");
        }

        // 4. Thực hiện đăng ký (Cập nhật 2 chiều)
        member.getEnrolledClasses().add(groupClass);
        member.setRemainingSessions(member.getRemainingSessions() - 1);
        groupClass.setCurrentEnrolled(groupClass.getCurrentEnrolled() + 1);

        memberRepository.save(member);
        groupClassRepository.save(groupClass);
    }

    // --- LOGIC QUAN TRỌNG: HỦY LỚP ---
    @Override
    @Transactional
    public void cancelEnrollment(Long memberId, Long classId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));
        GroupClass groupClass = groupClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        // Kiểm tra xem thực sự có trong lớp không trước khi hủy
        if (member.getEnrolledClasses().remove(groupClass)) {
            // Hoàn lại 1 buổi tập
            member.setRemainingSessions(member.getRemainingSessions() + 1);
            // Giảm sĩ số lớp
            groupClass.setCurrentEnrolled(Math.max(0, groupClass.getCurrentEnrolled() - 1));

            memberRepository.save(member);
            groupClassRepository.save(groupClass);
        } else {
            throw new RuntimeException("Bạn không tham gia lớp học này.");
        }
    }

    // --- LOGIC QUAN TRỌNG: NẠP BUỔI (ADMIN) ---
    @Override
    @Transactional
    public void addSessions(Long memberId, int amount) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));

        int current = (member.getRemainingSessions() != null) ? member.getRemainingSessions() : 0;
        member.setRemainingSessions(current + amount);

        memberRepository.save(member);
    }
}
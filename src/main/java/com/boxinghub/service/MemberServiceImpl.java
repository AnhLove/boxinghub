package com.boxinghub.service;

import com.boxinghub.entity.ClassStatus;
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
    @Transactional(readOnly = true)
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmailWithDetails(email);
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

        // 1. KIỂM TRA SỐ BUỔI TẬP CÒN LẠI (Quan trọng để fix lỗi -1)
        if (member.getRemainingSessions() == null || member.getRemainingSessions() <= 0) {
            throw new RuntimeException("Bạn không còn buổi tập nào. Vui lòng liên hệ Admin để nạp thêm!");
        }

        // 2. Kiểm tra đã đăng ký chưa
        if (member.getEnrolledClasses().contains(groupClass)) {
            throw new RuntimeException("Bạn đã đăng ký lớp này rồi!");
        }

        // 3. Kiểm tra sĩ số lớp
        if (groupClass.getCurrentEnrolled() >= groupClass.getCapacity()) {
            throw new RuntimeException("Lớp học đã đầy chỗ!");
        }

        // 4. Thực hiện nghiệp vụ khi đủ điều kiện
        member.getEnrolledClasses().add(groupClass);
        member.setRemainingSessions(member.getRemainingSessions() - 1); // Trừ buổi tập an toàn

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

    @Override
    @Transactional
    public void updateProfile(String email, Member data) {
        // Lấy member hiện tại từ DB để giữ lại các thông tin như danh sách lớp, id, v.v.
        Member member = memberRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));

        // Chỉ cập nhật các thông tin cá nhân cơ bản từ form
        member.setFullName(data.getFullName());
        member.setPhone(data.getPhone());
        member.setGender(data.getGender());
        member.setLevel(data.getLevel());
        member.setHeight(data.getHeight());
        member.setWeight(data.getWeight());

        // Cập nhật tên hiển thị bên bảng User để layout đồng bộ
        if (member.getUser() != null) {
            member.getUser().setFullName(data.getFullName());
        }

        memberRepository.save(member);
    }
}
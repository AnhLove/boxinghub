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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupClassRepository groupClassRepository;

    private final String UPLOAD_DIR = "D:/project/boxinghub/boxinghub/src/main/resources/static/uploads/avatars/";

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

    @Override
    @Transactional
    public void enrollInClass(Long memberId, Long classId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));
        GroupClass groupClass = groupClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        if (member.getRemainingSessions() == null || member.getRemainingSessions() <= 0) {
            throw new RuntimeException("Bạn không còn buổi tập nào. Vui lòng liên hệ Admin để nạp thêm!");
        }

        if (member.getEnrolledClasses().contains(groupClass)) {
            throw new RuntimeException("Bạn đã đăng ký lớp này rồi!");
        }

        if (groupClass.getCurrentEnrolled() >= groupClass.getCapacity()) {
            throw new RuntimeException("Lớp học đã đầy chỗ!");
        }

        member.getEnrolledClasses().add(groupClass);
        member.setRemainingSessions(member.getRemainingSessions() - 1);
        groupClass.setCurrentEnrolled(groupClass.getCurrentEnrolled() + 1);

        memberRepository.save(member);
        groupClassRepository.save(groupClass);
    }

    @Override
    @Transactional
    public void cancelEnrollment(Long memberId, Long classId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));
        GroupClass groupClass = groupClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

        if (member.getEnrolledClasses().remove(groupClass)) {
            member.setRemainingSessions(member.getRemainingSessions() + 1);
            groupClass.setCurrentEnrolled(Math.max(0, groupClass.getCurrentEnrolled() - 1));

            memberRepository.save(member);
            groupClassRepository.save(groupClass);
        } else {
            throw new RuntimeException("Bạn không tham gia lớp học này.");
        }
    }

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
    public void updateProfile(String email, Member data, MultipartFile avatarFile) {
        Member member = memberRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học viên"));


        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {

                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String originalFileName = avatarFile.getOriginalFilename();
                String extension = "";
                if (originalFileName != null && originalFileName.contains(".")) {
                    extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                }

                String fileName = "avatar_" + member.getId() + "_" + System.currentTimeMillis() + extension;
                Path filePath = Paths.get(UPLOAD_DIR + fileName);

                Files.copy(avatarFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                member.setAvatarUrl("/uploads/avatars/" + fileName);

            } catch (Exception e) {
                throw new RuntimeException("Lỗi hệ thống khi lưu ảnh: " + e.getMessage());
            }
        }

        member.setFullName(data.getFullName());
        member.setPhone(data.getPhone());
        member.setGender(data.getGender());
        member.setLevel(data.getLevel());
        member.setHeight(data.getHeight());
        member.setWeight(data.getWeight());


        if (member.getUser() != null) {
            member.getUser().setFullName(data.getFullName());
        }

        memberRepository.save(member);
    }
}
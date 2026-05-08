package com.boxinghub.controller.member;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.Member;
import com.boxinghub.entity.Trainer;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.ReviewService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberDashboardController {

    private final MemberService memberService;
    private final com.boxinghub.service.GroupClassService groupClassService;
    private final ReviewService reviewService;

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        String email = principal.getName();
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ học viên"));

        model.addAttribute("member", member);

        // Tạm thời truyền danh sách rỗng nếu chưa có logic Booking
        // Sau này sẽ là: model.addAttribute("myClasses", memberService.getEnrolledClasses(member.getId()));
        model.addAttribute("myClasses", new java.util.ArrayList<>());

        model.addAttribute("activePage", "dashboard");
        return "member/dashboard";
    }

    @PostMapping("/enroll/{classId}")
    public String enroll(@PathVariable Long classId, Principal principal, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        String email = principal.getName();
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Member"));

        try {
            memberService.enrollInClass(member.getId(), classId);
            ra.addFlashAttribute("success", "Đăng ký thành công! Bạn đã được thêm vào lớp.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage()); // lỗi "Hết buổi", "Lớp đầy"
        }

        return "redirect:/member/schedule";
    }

    @GetMapping("/schedule")
    @Transactional(readOnly = true)
    public String mySchedule(Principal principal, Model model) {
        String email = principal.getName();
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Member"));

        if (member.getEnrolledClasses() != null) {
            member.getEnrolledClasses().size();
        }

        var list = groupClassService.findAllAvailableForMembers();

        // 2. Tạo Map chứa điểm trung bình của từng Trainer trong danh sách lớp
        Map<Long, Double> ratings = list.stream()
                .map(GroupClass::getTrainer)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toMap(
                        Trainer::getId,
                        t -> reviewService.getAverageRating(t.getId())
                ));

        model.addAttribute("member", member);
        model.addAttribute("classes", list);
        model.addAttribute("ratings", ratings); // 3. Truyền Map ratings sang HTML
        model.addAttribute("activePage", "schedule");
        model.addAttribute("pageTitle", "Đăng ký tập");

        return "member/schedule";
    }

    // Thêm Endpoint Hủy lớp
    @PostMapping("/cancel/{classId}")
    public String cancel(@PathVariable Long classId, Principal principal, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {
        String email = principal.getName();
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Member"));

        try {
            // Bạn cần thêm hàm cancelEnrollment vào MemberService
            memberService.cancelEnrollment(member.getId(), classId);
            ra.addFlashAttribute("success", "Đã hủy đăng ký và hoàn lại 1 buổi tập.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể hủy lớp: " + e.getMessage());
        }
        return "redirect:/member/dashboard"; // Hủy xong về dashboard xem lịch
    }

    @GetMapping("/history")
    @Transactional(readOnly = true)
    public String history(Principal principal, Model model) {
        String email = principal.getName();
        Member member = memberService.getMemberByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Member"));

        if (member.getEnrolledClasses() != null) {
            member.getEnrolledClasses().size();
        }
        // Lấy danh sách lớp đã đăng ký từ đối tượng member
        model.addAttribute("enrolledClasses", member.getEnrolledClasses());
        model.addAttribute("member", member);
        model.addAttribute("activePage", "history");
        model.addAttribute("pageTitle", "Lịch sử tập luyện");

        return "member/history";
    }
}
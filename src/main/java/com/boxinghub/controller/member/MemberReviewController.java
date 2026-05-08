package com.boxinghub.controller.member;

import com.boxinghub.entity.Member;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member/review")
@RequiredArgsConstructor
public class MemberReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    @PostMapping("/submit")
    public String submitReview(
            @RequestParam("trainerId") Long trainerId,
            @RequestParam("rating") int rating,
            @RequestParam(value = "comment", required = false) String comment,
            Principal principal,
            RedirectAttributes ra) {

        String email = principal.getName();

        try {
            // 1. Lấy thông tin Member từ email đăng nhập
            Member member = memberService.getMemberByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ học viên"));

            // 2. CHỖ NÀY ĐÃ SỬA: Không chặn hasMemberReviewedTrainer nữa
            // để logic Service tự động xử lý việc CẬP NHẬT (Update) nếu đã tồn tại.

            // 3. Gọi service để lưu đánh giá (Sẽ tự Update nếu đã có bản cũ)
            reviewService.submitReview(member.getId(), trainerId, rating, comment);

            ra.addFlashAttribute("success", "Đã ghi nhận đánh giá của bạn!");

        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Sau khi đánh giá xong, quay lại trang Lịch sử tập luyện
        return "redirect:/member/history";
    }
}
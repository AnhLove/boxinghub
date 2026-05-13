package com.boxinghub.controller.admin;

import com.boxinghub.entity.Report;
import com.boxinghub.repository.CommentRepository;
import com.boxinghub.repository.PostRepository;
import com.boxinghub.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public String listReports(Model model) {
        model.addAttribute("reports", reportRepository.findAllWithDetails());
        model.addAttribute("pendingCount", reportRepository.countByStatus(Report.ReportStatus.PENDING));
        model.addAttribute("activePage", "admin-reports");
        model.addAttribute("pageTitle", "Báo cáo vi phạm");
        return "admin/reports/list";
    }

    @PostMapping("/resolve/{id}")
    @Transactional
    public String resolveReport(@PathVariable Long id,
                                @RequestParam String action,
                                RedirectAttributes redirectAttributes) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found: " + id));

        if ("DELETE".equals(action)) {
            if (report.getPost() != null) {
                Long postId = report.getPost().getId();
                reportRepository.deleteAll(reportRepository.findByPostId(postId));
                postRepository.deleteById(postId);

            } else if (report.getComment() != null) {
                Long commentId = report.getComment().getId();
                reportRepository.deleteAll(reportRepository.findByCommentId(commentId));
                commentRepository.deleteById(commentId);
            }
            redirectAttributes.addFlashAttribute("successMsg", "Đã xóa nội dung vi phạm.");

        } else if ("DISMISS".equals(action)) {
            report.setStatus(Report.ReportStatus.DISMISSED);
            reportRepository.save(report);
            redirectAttributes.addFlashAttribute("successMsg", "Đã bỏ qua báo cáo.");
        }

        return "redirect:/admin/reports";
    }
}
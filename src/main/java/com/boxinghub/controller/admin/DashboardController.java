package com.boxinghub.controller.admin;

import com.boxinghub.entity.ClassStatus;
import com.boxinghub.service.GroupClassService;
import com.boxinghub.service.MemberService;
import com.boxinghub.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final MemberService memberService;
    private final TrainerService trainerService;
    private final GroupClassService groupClassService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        // Thống kê số lượng
        model.addAttribute("totalMembers", memberService.getAllMembers().size());
        model.addAttribute("totalTrainers", trainerService.getAllTrainers().size());
        model.addAttribute("totalClasses", groupClassService.getAllGroupClasses().size());
        model.addAttribute("openClasses", groupClassService.findByStatus(ClassStatus.OPEN).size());

        // Lấy danh sách lớp học sắp tới (trong 7 ngày tới) để hiển thị bảng
        // Dựa trên hàm getUpcomingClasses() bạn đã viết ở GroupClassService
        model.addAttribute("upcomingClasses", groupClassService.getUpcomingClasses());

        return "admin/dashboard";
    }
}
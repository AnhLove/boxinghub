package com.boxinghub.controller.admin;

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

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("totalMembers",
                memberService.getAllMembers().size());
        model.addAttribute("totalTrainers",
                trainerService.getAllTrainers().size());
        return "admin/dashboard";
    }
}
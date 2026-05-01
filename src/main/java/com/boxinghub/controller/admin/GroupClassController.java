package com.boxinghub.controller.admin;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import com.boxinghub.service.GroupClassService;
import com.boxinghub.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/group-classes")
public class GroupClassController {

    private final GroupClassService groupClassService;
    private final TrainerService trainerService;

    public GroupClassController(GroupClassService groupClassService, TrainerService trainerService) {
        this.groupClassService = groupClassService;
        this.trainerService = trainerService;
    }

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword,
                       Model model) {
        // Quan trọng: Nếu keyword là null hoặc rỗng, Service phải trả về findAll()
        List<GroupClass> groupClasses = groupClassService.findByClassName(keyword);

        model.addAttribute("groupClasses", groupClasses);
        model.addAttribute("keyword", keyword);
        return "admin/group-classes/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("groupClass", new GroupClass());

        // Đổ dữ liệu cần thiết cho Form
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("statuses", ClassStatus.values());

        model.addAttribute("action", "/admin/group-classes/save");
        model.addAttribute("isEdit", false);
        return "admin/group-classes/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        return groupClassService.getGroupClassById(id)
                .map(gc -> {
                    model.addAttribute("groupClass", gc);

                    // Đổ dữ liệu cần thiết cho Form khi sửa
                    model.addAttribute("trainers", trainerService.getAllTrainers());
                    model.addAttribute("statuses", ClassStatus.values());

                    model.addAttribute("action", "/admin/group-classes/save");
                    model.addAttribute("isEdit", true);
                    return "admin/group-classes/form";
                })
                .orElse("redirect:/admin/group-classes");
    }

    @PostMapping("/save")
    public String saveGroupClass(@ModelAttribute GroupClass groupClass,
                                 @RequestParam("trainerId") Long trainerId) {

        // Tìm HLV từ trainerId được gửi lên và gán vào GroupClass
        trainerService.getTrainerById(trainerId).ifPresent(groupClass::setTrainer);

        groupClassService.saveGroupClass(groupClass);
        return "redirect:/admin/group-classes";
    }

    @GetMapping("/delete/{id}")
    public String deleteGroupClass(@PathVariable Long id) {
        groupClassService.deleteGroupClass(id);
        return "redirect:/admin/group-classes";
    }
}
package com.boxinghub.controller.admin;

import com.boxinghub.entity.GroupClass;
import com.boxinghub.entity.ClassStatus;
import com.boxinghub.service.GroupClassService;
import com.boxinghub.service.TrainerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.ResponseBody;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        model.addAttribute("activePage", "group-classes");
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

    @GetMapping("/schedule")
    public String showSchedule(Model model) {
        model.addAttribute("activePage", "schedule");

        return "admin/group-classes/schedule";
    }

    // API trả về dữ liệu JSON cho FullCalendar
    @GetMapping("/api/events")
    @ResponseBody // Quan trọng: Trả về dữ liệu JSON thay vì tìm file HTML
    public List<Map<String, Object>> getCalendarEvents() {
        // Lấy tất cả lớp học (Bạn cần đảm bảo Service có hàm getAllGroupClasses hoặc tương tự)
        List<GroupClass> classes = groupClassService.findByClassName(null);

        List<Map<String, Object>> events = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        for (GroupClass gc : classes) {
            Map<String, Object> event = new HashMap<>();
            event.put("id", gc.getId());

            // Hiển thị: Tên lớp - Tên HLV
            String trainerName = (gc.getTrainer() != null) ? gc.getTrainer().getFullName() : "Chưa có HLV";
            event.put("title", gc.getClassName() + " (" + trainerName + ")");

            // Thời gian bắt đầu (Từ cột schedule trong DB của bạn)
            if (gc.getSchedule() != null) {
                event.put("start", gc.getSchedule().format(formatter));

                // Giả sử mỗi ca tập là 90 phút (bạn có thể chỉnh lại tùy ý)
                event.put("end", gc.getSchedule().plusMinutes(90).format(formatter));
            }

            // Màu sắc dựa trên trạng thái
            if (gc.getStatus() == ClassStatus.OPEN) {
                event.put("backgroundColor", "#10b981"); // Xanh lá
                event.put("borderColor", "#10b981");
            } else {
                event.put("backgroundColor", "#e11d48"); // Đỏ
                event.put("borderColor", "#e11d48");
            }

            // Đường dẫn khi click vào sự kiện sẽ dẫn thẳng đến trang chỉnh sửa
            event.put("url", "/admin/group-classes/edit/" + gc.getId());

            events.add(event);
        }
        return events;
    }
}
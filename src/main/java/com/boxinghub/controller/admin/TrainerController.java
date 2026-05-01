package com.boxinghub.controller.admin;

import com.boxinghub.entity.Trainer;
import com.boxinghub.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public String listTrainers(@RequestParam(name = "keyword", required = false) String keyword,
                               Model model) {
        List<Trainer> trainers = trainerService.searchByName(keyword);

        model.addAttribute("trainers", trainers);
        model.addAttribute("keyword", keyword);
        model.addAttribute("activePage", "trainers");

        return "admin/trainers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "admin/trainers/form";
    }

    @PostMapping("/save")
    public String saveTrainer(@ModelAttribute Trainer trainer) {
        trainerService.saveTrainer(trainer);
        return "redirect:/admin/trainers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        trainerService.getTrainerById(id).ifPresent(
                trainer -> model.addAttribute("trainer", trainer)
        );
        return "admin/trainers/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return "redirect:/admin/trainers";
    }
}
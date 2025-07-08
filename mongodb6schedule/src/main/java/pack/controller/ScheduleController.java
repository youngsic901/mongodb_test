package pack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pack.model.Schedule;
import pack.model.ScheduleService;

import java.util.List;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping
    public String getAllSchedules(Model model) {
        List<Schedule> schedules = service.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "list";
    }

    @GetMapping("/new")
    public String scheduleForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "create";
    }

    @PostMapping
    public String createSchedules(@ModelAttribute("schedule")Schedule schedule) {
        service.createSchedule(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable(name = "id")String id, Model model) {
        Schedule schedule = service.getScheduleById(id).orElseThrow(() -> new IllegalArgumentException("invalid id : " + id));
        model.addAttribute("schedule", schedule);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateSchedule(@PathVariable(name = "id")String id,
                                 @ModelAttribute("schedule") Schedule schedule) {
        service.updateSchedule(id, schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable(name = "id")String id) {
        service.deleteSchedule(id);
        return "redirect:/schedules";
    }
}

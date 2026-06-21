package com.atul.event.management.controller;

import com.atul.event.management.entity.Registration;
import com.atul.event.management.repository.RegistrationRepository;
import com.atul.event.management.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final RegistrationRepository registrationRepository;

    @GetMapping("/admin/attendance")
    public String attendance(Model model) {
        model.addAttribute("registrations", registrationRepository.findAll());
        model.addAttribute("attendanceRecords", attendanceService.getAllAttendance());
        return "attendance";
    }

    @GetMapping("/admin/attendance/mark/{registrationId}")
    public String markAttendance(
            @PathVariable Long registrationId,
            RedirectAttributes redirectAttributes
    ) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid registration id: " + registrationId
                ));

        try {
            attendanceService.markAttendance(registration);
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully.");
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }

        return "redirect:/admin/attendance";
    }
}

package com.atul.event.management.controller;

import com.atul.event.management.entity.Event;
import com.atul.event.management.repository.RegistrationRepository;
import com.atul.event.management.service.ReportService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final RegistrationRepository registrationRepository;

    @GetMapping("/admin/reports")
    public String reports(Model model) {
        List<Event> events = reportService.getAllEvents();
        Map<Long, Integer> registrationCounts = events.stream()
                .collect(Collectors.toMap(
                        Event::getId,
                        event -> registrationRepository.findByEvent(event).size()
                ));

        model.addAttribute("totalUsers", reportService.getTotalUsers());
        model.addAttribute("totalEvents", reportService.getTotalEvents());
        model.addAttribute("totalRegistrations", reportService.getTotalRegistrations());
        model.addAttribute("totalAttendance", reportService.getTotalAttendance());
        model.addAttribute("totalFeedback", reportService.getTotalFeedback());
        model.addAttribute("events", events);
        model.addAttribute("registrationCounts", registrationCounts);

        return "reports";
    }
}

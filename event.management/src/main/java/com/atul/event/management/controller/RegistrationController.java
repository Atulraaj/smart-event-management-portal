package com.atul.event.management.controller;

import com.atul.event.management.entity.Event;
import com.atul.event.management.entity.User;
import com.atul.event.management.repository.UserRepository;
import com.atul.event.management.service.EventService;
import com.atul.event.management.service.FeedbackService;
import com.atul.event.management.service.RegistrationService;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final EventService eventService;
    private final FeedbackService feedbackService;
    private final UserRepository userRepository;

    @PostMapping("/user/events/{id}/register")
    public String registerForEvent(
            @PathVariable Long id,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        User user = getCurrentUser(principal);
        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event id: " + id));

        try {
            registrationService.registerUserForEvent(user, event);
            redirectAttributes.addFlashAttribute("success", "Event registration completed successfully.");
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }

        return "redirect:/user/events/" + id;
    }

    @GetMapping("/user/registrations")
    public String myRegistrations(Principal principal, Model model) {
        User user = getCurrentUser(principal);
        Set<Long> submittedFeedbackEventIds = feedbackService.getFeedbackByUser(user).stream()
                .map(feedback -> feedback.getEvent().getId())
                .collect(Collectors.toSet());

        model.addAttribute("registrations", registrationService.getRegistrationsByUser(user));
        model.addAttribute("submittedFeedbackEventIds", submittedFeedbackEventIds);
        return "my-registrations";
    }

    private User getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user was not found."));
    }
}

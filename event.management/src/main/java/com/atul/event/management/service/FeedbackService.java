package com.atul.event.management.service;

import com.atul.event.management.entity.Attendance;
import com.atul.event.management.entity.Event;
import com.atul.event.management.entity.Feedback;
import com.atul.event.management.entity.Registration;
import com.atul.event.management.entity.User;
import com.atul.event.management.repository.AttendanceRepository;
import com.atul.event.management.repository.FeedbackRepository;
import com.atul.event.management.repository.RegistrationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final RegistrationRepository registrationRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Feedback saveFeedback(User user, Event event, Integer rating, String comments) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        if (feedbackRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException(
                    "You have already submitted feedback for this event."
            );
        }

        Registration registration = registrationRepository.findByUser(user).stream()
                .filter(item -> item.getEvent().getId().equals(event.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "You must register for this event before submitting feedback."
                ));

        boolean attended = attendanceRepository.findByRegistration(registration)
                .map(Attendance::getPresent)
                .orElse(false);

        if (!attended) {
            throw new IllegalStateException(
                    "Feedback can be submitted only after attendance is marked present."
            );
        }

        Feedback feedback = Feedback.builder()
                .user(user)
                .event(event)
                .rating(rating)
                .comments(comments)
                .submittedAt(LocalDateTime.now())
                .build();

        return feedbackRepository.save(feedback);
    }

    @Transactional(readOnly = true)
    public List<Feedback> getFeedbackByEvent(Event event) {
        return feedbackRepository.findByEvent(event);
    }

    @Transactional(readOnly = true)
    public List<Feedback> getFeedbackByUser(User user) {
        return feedbackRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}

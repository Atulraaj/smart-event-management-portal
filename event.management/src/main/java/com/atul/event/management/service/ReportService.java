package com.atul.event.management.service;

import com.atul.event.management.entity.Event;
import com.atul.event.management.repository.AttendanceRepository;
import com.atul.event.management.repository.EventRepository;
import com.atul.event.management.repository.FeedbackRepository;
import com.atul.event.management.repository.RegistrationRepository;
import com.atul.event.management.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final AttendanceRepository attendanceRepository;
    private final FeedbackRepository feedbackRepository;

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalEvents() {
        return eventRepository.count();
    }

    public long getTotalRegistrations() {
        return registrationRepository.count();
    }

    public long getTotalAttendance() {
        return attendanceRepository.count();
    }

    public long getTotalFeedback() {
        return feedbackRepository.count();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}

package com.atul.event.management.service;

import com.atul.event.management.entity.Attendance;
import com.atul.event.management.entity.Registration;
import com.atul.event.management.repository.AttendanceRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private static final String MANUAL_CHECK_IN = "MANUAL";

    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Attendance markAttendance(Registration registration) {
        if (isAttendanceMarked(registration)) {
            throw new IllegalStateException("Attendance has already been marked for this registration.");
        }

        Attendance attendance = Attendance.builder()
                .registration(registration)
                .present(true)
                .checkInTime(LocalDateTime.now())
                .checkInMethod(MANUAL_CHECK_IN)
                .build();

        return attendanceRepository.save(attendance);
    }

    @Transactional(readOnly = true)
    public boolean isAttendanceMarked(Registration registration) {
        return attendanceRepository.findByRegistration(registration).isPresent();
    }

    @Transactional(readOnly = true)
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
}

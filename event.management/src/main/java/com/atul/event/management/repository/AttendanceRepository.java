package com.atul.event.management.repository;

import com.atul.event.management.entity.Attendance;
import com.atul.event.management.entity.Registration;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByRegistration(Registration registration);
}
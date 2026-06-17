package com.atul.event.management.repository;

import com.atul.event.management.entity.Event;
import com.atul.event.management.entity.Registration;
import com.atul.event.management.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    boolean existsByUserAndEvent(User user, Event event);

    List<Registration> findByUser(User user);

    List<Registration> findByEvent(Event event);
}
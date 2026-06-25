package com.atul.event.management.repository;

import com.atul.event.management.entity.Event;
import com.atul.event.management.entity.Feedback;
import com.atul.event.management.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByEvent(Event event);

    List<Feedback> findByUser(User user);

    boolean existsByUserAndEvent(User user, Event event);
}

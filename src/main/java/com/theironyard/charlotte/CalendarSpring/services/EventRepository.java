package com.theironyard.charlotte.CalendarSpring.services;

import com.theironyard.charlotte.CalendarSpring.entities.Event;
import com.theironyard.charlotte.CalendarSpring.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {
    List<Event> findAllByOrderByStartDateTimeDesc();

    // see if
    List<Event> findAllByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);

    // AND see that this statement between
    List<Event> findAllByEndDateTimeBetween(LocalDateTime start, LocalDateTime end);

    // WE NEED:
    List <Event> findAllByUser(User user);
    // List<Event> findsEventsByTheUser(we have to pass in a user);

}
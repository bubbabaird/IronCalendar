package com.theironyard.charlotte.CalendarSpring.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    LocalDateTime startDateTime;

    @Column(nullable = false)
    LocalDateTime endDateTime;

    // each user can have many events
    @ManyToOne
    User user;

    public Event() {
    }

    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime, User user) {
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.user = user;
    }
}
package com.theironyard.charlotte.CalendarSpring.controllers;

import com.theironyard.charlotte.CalendarSpring.entities.Event;
import com.theironyard.charlotte.CalendarSpring.entities.User;
import com.theironyard.charlotte.CalendarSpring.services.EventRepository;
import com.theironyard.charlotte.CalendarSpring.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class CalendarSpringController {
    @Autowired
    EventRepository events;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        List<Event> eventEntities = events.findAllByOrderByStartDateTimeDesc();
        List<Event> userEvents;
        // use the events repository to find all events by a user. like. "findAllByUser"

        if (userName != null) {
            User user = users.findFirstByName(userName);
            model.addAttribute("user", user);
            model.addAttribute("now", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            userEvents = events.findAllByUser(user);
            //model.addAttribute = events.findAllByUser(user);
            model.addAttribute("userEvents", userEvents);

        }
        model.addAttribute("events", eventEntities);

        return "home";
    }

    @RequestMapping(path = "/create-event", method = RequestMethod.POST)
    public String createEvent(HttpSession session, String description, String startDateTime, String endDateTime) {
        String userName = (String) session.getAttribute("userName");
        if (userName != null) {
            Event event = new Event(description, LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime), users.findFirstByName(userName));
            // our goal is to make sure there are NO events
            //   where the start time is between the NEW event's start and end times
            // AND where the end time is between the NEW event's start and end times.

            // if the length of collidingStartTimes is greater than 0 OR
            // the length of collidingEndTimes is greater than 0
            // we can't save this new event.
            List<Event> collidingStartTimes = events.findAllByStartDateTimeBetween(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime));
            List<Event> collidingEndTimes = events.findAllByEndDateTimeBetween(LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime));
            LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime userDateTime = LocalDateTime.parse(startDateTime);

            if (collidingStartTimes.size() == 0 && collidingEndTimes.size() == 0) {
            // if the event time you're given (above) is between [all start dates]

                // Disallow a user from creating an event in the past, so to do that:
                // is user startDateTime before LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                if (userDateTime.isAfter(currentDateTime)) {
                    events.save(event);
                }
            }
        }
        return "redirect:/";
    }
    // When there is no user logged in, display all events


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String name) {
        User user = users.findFirstByName(name);
        if (user == null) {
            user = new User(name);
            users.save(user);
        }
        session.setAttribute("userName", name);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
package pl.com.psl.spring.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.com.psl.spring.event.service.EventService;
import pl.com.psl.spring.event.service.entity.Event;

import java.util.List;

/**
 * Created by psl on 25.08.17.
 */
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<Event>> getEvents(){
        List<Event> events = eventService.getEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}

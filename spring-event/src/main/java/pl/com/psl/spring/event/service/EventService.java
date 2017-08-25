package pl.com.psl.spring.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.com.psl.spring.event.service.entity.Event;
import pl.com.psl.spring.event.service.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by psl on 25.08.17.
 */
@Component
public class EventService {

    private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    @EventListener
    public void handleEvent(Event event){
        LOG.info("Handling event={}", event);
        eventRepository.save(event);
    }

    public List<Event> getEvents(){
        LOG.info("Getting all events...");
        Iterable<Event> all = eventRepository.findAll();
        List<Event> events = new ArrayList<>();
        all.forEach(events::add);
        LOG.info("Got events={}", events);
        return events;
    }
}

package pl.com.psl.spring.event.service.repository;

import org.springframework.data.repository.CrudRepository;
import pl.com.psl.spring.event.service.entity.Event;

/**
 * Created by psl on 25.08.17.
 */
public interface EventRepository extends CrudRepository<Event, Long> {
}

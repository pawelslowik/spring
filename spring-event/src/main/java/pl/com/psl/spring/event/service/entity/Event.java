package pl.com.psl.spring.event.service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Created by psl on 25.08.17.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;
    private Instant timestamp;
    private String message;

    public Event(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }
}

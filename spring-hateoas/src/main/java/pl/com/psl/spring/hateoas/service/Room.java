package pl.com.psl.spring.hateoas.service;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by psl on 13.08.17.
 */
public class Room extends ResourceSupport {

    private Long id;
    private String name;
    private String description;

    public Room(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getRoomId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

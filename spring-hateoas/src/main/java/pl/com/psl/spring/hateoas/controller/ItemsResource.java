package pl.com.psl.spring.hateoas.controller;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * Created by psl on 19.08.17.
 *
 * Wrapper for list of resources, required to produce HAL-compliant json.
 */
public class ItemsResource<T> extends ResourceSupport {

    private List<T> items;

    public ItemsResource(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }
}

package pl.com.psl.spring.cloudcontract.producer;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@RestController
public class ProducerController {

    private Map<Long, Resource> resources = new HashMap<>();

    @GetMapping(value = "/resources/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Resource> get(@PathVariable long id) {
        return ofNullable(resources.get(id)).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/resources", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity post(@RequestBody ResourceRequest request) {
        Long key = resources.keySet().stream().max(Long::compareTo).orElse(0L);
        resources.put(key, new Resource(key, request.getStringValue()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

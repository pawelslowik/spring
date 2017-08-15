package pl.com.psl.spring.hateoas.service.repository;

import org.springframework.data.repository.CrudRepository;
import pl.com.psl.spring.hateoas.service.entity.Room;

/**
 * Created by psl on 15.08.17.
 */
public interface RoomRepository extends CrudRepository<Room, Long>{
}

package pl.com.psl.spring.hateoas.service.repository;

import org.springframework.data.repository.CrudRepository;
import pl.com.psl.spring.hateoas.service.entity.Booking;

import java.util.List;

/**
 * Created by psl on 15.08.17.
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);
}

package pl.com.psl.spring.hateoas.service;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by psl on 15.08.17.
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);
}

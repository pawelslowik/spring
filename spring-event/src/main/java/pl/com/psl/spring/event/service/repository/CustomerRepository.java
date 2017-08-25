package pl.com.psl.spring.event.service.repository;

import org.springframework.data.repository.CrudRepository;
import pl.com.psl.spring.event.service.entity.Customer;

/**
 * Created by psl on 25.08.17.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{
}

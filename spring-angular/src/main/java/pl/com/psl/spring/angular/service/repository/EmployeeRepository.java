package pl.com.psl.spring.angular.service.repository;

import org.springframework.data.repository.CrudRepository;
import pl.com.psl.spring.angular.service.entity.Employee;

/**
 * Created by psl on 05.09.17.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>{
}

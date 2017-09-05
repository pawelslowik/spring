package pl.com.psl.spring.angular.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.com.psl.spring.angular.service.entity.Employee;
import pl.com.psl.spring.angular.service.repository.EmployeeRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by psl on 05.09.17.
 */
@Component
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void init(){
        List<Employee> employees = Arrays.asList(
                new Employee("bob", 34, "wroclaw"),
                new Employee("tom", 22, "warsaw"),
                new Employee("pat", 22, "warsaw"));
        employeeRepository.save(employees);
        LOG.info("Initialized DB with employees={}", employees);
    }

    public List<Employee> getEmployees(){
        LOG.info("Getting employees...");
        Iterable<Employee> all = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        all.forEach(employees::add);
        LOG.info("Got employees={}", employees);
        return employees;
    }

    public Employee createEmployee(Employee employee){
        LOG.info("Creating employee={}", employee);
        Employee createdEmployee = employeeRepository.save(employee);
        LOG.info("Created employee={}", createdEmployee);
        return createdEmployee;
    }

    public Employee deleteEmployee(Long id){
        LOG.info("Deleting employee by id={}", id);
        Employee employee = employeeRepository.findOne(id);
        if(employee == null){
            throw new RuntimeException("Employee with id=" + id + " does not exist!");
        }
        LOG.info("Found employee by id={} -> {}", id, employee);
        employeeRepository.delete(employee);
        LOG.info("Employee with id={} deleted!", id);
        return employee;
    }
}

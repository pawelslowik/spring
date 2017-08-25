package pl.com.psl.spring.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.com.psl.spring.event.service.entity.Customer;
import pl.com.psl.spring.event.service.entity.Event;
import pl.com.psl.spring.event.service.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by psl on 25.08.17.
 */
@Component
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public Customer createCustomer(Customer customer){
        LOG.info("Adding customer={}...", customer);
        Customer createdCustomer = customerRepository.save(customer);
        LOG.info("Customer created={}", createdCustomer);
        applicationEventPublisher.publishEvent(new Event("Created customer with id=" + createdCustomer.getId()));
        return customer;
    }

    public List<Customer> getCustomers(){
        LOG.info("Getting all customers...");
        Iterable<Customer> all = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        all.forEach(customers::add);
        LOG.info("Got customers={}", customers);
        return customers;
    }

    public Customer deleteCustomer(Long id){
        LOG.info("Deleting customer with id={}...", id);
        Customer customer = customerRepository.findOne(id);
        if(Objects.isNull(customer)){
            LOG.error("Customer does not exist");
            applicationEventPublisher.publishEvent(new Event("Tried to delete non-existing customer with id=" + id));
            LOG.error("published");
            return null;
        }
        customerRepository.delete(customer);
        LOG.info("Customer deleted={}", customer);
        applicationEventPublisher.publishEvent(new Event("Deleted customer with id=" + customer.getId()));
        return customer;
    }
}

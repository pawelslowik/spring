package pl.com.psl.spring.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.psl.spring.event.service.CustomerService;
import pl.com.psl.spring.event.service.entity.Customer;

import java.util.List;
import java.util.Objects;

/**
 * Created by psl on 25.08.17.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<Customer>> getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity<Customer> deleteCustomer(@PathVariable Long id){
        Customer customer = customerService.deleteCustomer(id);
        return new ResponseEntity<>(customer, Objects.isNull(customer) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}

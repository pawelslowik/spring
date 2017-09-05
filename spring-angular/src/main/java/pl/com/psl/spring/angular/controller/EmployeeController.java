package pl.com.psl.spring.angular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.com.psl.spring.angular.service.entity.Employee;
import pl.com.psl.spring.angular.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(path = "/")
    public String app() {
        return "app.html";
    }

    @RequestMapping(path = "/employees", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Response> getEmployees() {
        return new ResponseEntity<>(new Response(employeeService.getEmployees(), "Successfully got all employees"), HttpStatus.OK);
    }

    @RequestMapping(path = "/employees", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Response> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(new Response(employeeService.createEmployee(employee), "Successfully created employee"), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpEntity<Response> deleteEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(new Response(employeeService.deleteEmployee(id), "Successfully deleted employee"), HttpStatus.OK);
    }
}

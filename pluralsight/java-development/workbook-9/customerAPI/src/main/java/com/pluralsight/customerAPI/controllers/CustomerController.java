package com.pluralsight.customerAPI.controllers;

import com.pluralsight.customerAPI.dao.interfaces.ICustomerDAO;
import com.pluralsight.customerAPI.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    private ICustomerDAO dao;

    @Autowired
    public CustomerController(ICustomerDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/customers", method = RequestMethod.GET)
    public List<Customer> getCustomers(@RequestParam(required = false) String name) {
        List<Customer> customers = dao.getAll();
        return customers;
    }


    @RequestMapping(path = "/customers/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable String customerId) {
        Customer customer = dao.findCustomerById(customerId);
        return customer;
    }

    @RequestMapping(path = "/customers", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody Customer customer) {
        return dao.insert(customer);
    }

    @RequestMapping(path = "/customers/{customerId}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable String customerId, @RequestBody Customer customer) {
        dao.update(customerId, customer);
    }

    @RequestMapping(path = "/customers/{customerId}", method = RequestMethod.DELETE )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String customerId){
        dao.delete(customerId);
    }
}

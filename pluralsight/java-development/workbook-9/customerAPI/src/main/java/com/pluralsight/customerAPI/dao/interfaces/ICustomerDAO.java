package com.pluralsight.customerAPI.dao.interfaces;

import com.pluralsight.customerAPI.models.Customer;

import java.util.List;

public interface ICustomerDAO {
    List<Customer> getAll();

    Customer findCustomerById(String id);

    Customer insert(Customer customer);

    void update(String id, Customer customer);

    void delete(String id);
}

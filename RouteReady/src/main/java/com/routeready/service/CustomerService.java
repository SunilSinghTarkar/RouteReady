package com.routeready.service;

import java.util.List;

import com.routeready.model.Customer;
import com.routeready.model.CustomerDto;

public interface CustomerService {
    public Customer addNewCustomer(Customer customer);
    public Customer updateCustomer(CustomerDto customer,Integer customerId);

    public Customer deleteCustomer(Integer customerId);

    public List<Customer> getAllCustomers();

    public Customer getCustomerById(Integer customerId);

    public Customer validateCustomer(String username,String password);

    public Customer unBlockCustomer(Integer customerId);

    public Customer getCustomerByUsername(String username);
}

package com.routeready.service;

import java.util.List;

import com.routeready.model.Customer;

public interface CustomerService  {
  public Customer crateCustomer(Customer customer);
  public Customer getCustomer(Integer customerId);
  public List<Customer> getAllCustomer( );
  public Customer updateCustomer(String address,String mobileNumber,Integer customerId);
  public String deleteCustomer(Integer customerId);
  
}

package com.routeready.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeready.exception.NotFoundException;
import com.routeready.exception.RouteReadyException;
import com.routeready.model.Customer;
import com.routeready.model.User;
import com.routeready.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public Customer crateCustomer(Customer customer) {
		if (customer == null)
			throw new RouteReadyException("Please provide valid customer");
		Customer cust = customerRepo.save(customer);
		return cust;
	}

	@Override
	public Customer getCustomer(Integer customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new NotFoundException("Customer not found with given Id; " + customerId));
		
		if (!customer.isActive())
			throw new NotFoundException("Customer not found with given Id; " + customerId);
		return customer;
	}

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customerList = customerRepo.findAll();
		if (customerList.isEmpty())
			throw new NotFoundException("Customer not found!");
		return customerList;
	}

	@Override
	public Customer updateCustomer(String address, String mobileNumber, Integer customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new NotFoundException("Customer not found with given Id; " + customerId));
		
		customer.setAddress(address);
		customer.setMobileNumber(mobileNumber);

		return customerRepo.save(customer);
	}

	@Override
	public String deleteCustomer(Integer customerId) {
		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new NotFoundException("Customer not found with given Id; " + customerId));
		
		customer.setActive(false);
		customerRepo.save(customer);
		return "Customer deleted succesfully";
	}

}

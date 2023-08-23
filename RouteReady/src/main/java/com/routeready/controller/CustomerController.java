package com.routeready.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routeready.model.Customer;
import com.routeready.service.CustomerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/cutomers")
public class CustomerController {
	@Autowired
	private CustomerService service;

	@PostMapping("/")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer cust = service.crateCustomer(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);
	}
	
	@GetMapping("/{cusotmerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer  cusotmerId) {
		Customer cust = service.getCustomer(cusotmerId);
		return new ResponseEntity<Customer>(cust, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Customer>> getCustomer() {
		List<Customer> list = service.getAllCustomer();
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}
	
	@PatchMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@RequestParam String address,@RequestParam String mobileNumber,@PathVariable Integer customerId) {
	Customer customer = service.updateCustomer(address, mobileNumber, customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
		String str = service.deleteCustomer( customerId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
	
	
	
	
}

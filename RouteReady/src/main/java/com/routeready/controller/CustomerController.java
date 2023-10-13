package com.routeready.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routeready.model.Admin;
import com.routeready.model.Customer;
import com.routeready.model.CustomerDto;
import com.routeready.model.Driver;
import com.routeready.model.UserDto;
import com.routeready.service.AdminService;
import com.routeready.service.CustomerService;
import com.routeready.service.DriverService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private DriverService driverService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private PasswordEncoder encoder;

	// A simple test method to return a "Hello" message.
	@GetMapping("/hello")
	public String helloMethodFromCustomer() {
		return "Hello from Customer Controller";
	}

	// Add a new Customer
	@PostMapping("/add")
	public ResponseEntity<Customer> addNewCustomer(@Valid @RequestBody Customer customer) {
		log.info("Try to add new Customer : CustomerController");
		customer.setPassword(encoder.encode(customer.getPassword()));
		customer.setRole("ROLE_CUSTOMER");
		Customer savedCustomer = customerService.addNewCustomer(customer);
		log.info("Customer added successfully : CustomerController");
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	// Update an existing Customer
	@PatchMapping("/customer/{customerId}")
	public ResponseEntity<Customer> updateExistingCustomer(@RequestBody CustomerDto customer,
			@PathVariable Integer customerId) {
		log.info("Try to update Customer : CustomerController");
		Customer updateCustomer = customerService.updateCustomer(customer, customerId);
		log.info("Customer updated successfully : CustomerController");
		return new ResponseEntity<>(updateCustomer, HttpStatus.CREATED);
	}

	// Delete an existing Customer
	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<Customer> deleteExistingCustomer(@PathVariable Integer customerId) {
		log.info("Try to delete Customer : CustomerController");
		Customer deletedCustomer = customerService.deleteCustomer(customerId);
		log.info("Deleted Customer successfully : CustomerController");
		return new ResponseEntity<>(deletedCustomer, HttpStatus.OK);
	}

	// Get a list of all Customers
	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		log.info("Try to get all Customers : CustomerController");
		List<Customer> customerList = customerService.getAllCustomers();
		log.info("Get all Customers successful : CustomerController");
		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}

	// Get Customer by ID
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer customerId) {
		log.info("Try to get Customer : CustomerController");
		Customer customer = customerService.getCustomerById(customerId);
		log.info("Fetched Customer successfully : CustomerController");
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	// Get details of the logged-in Customer
	@GetMapping("/signIn")
	public ResponseEntity<UserDto> getLoggedInCustomerDetailsHandler(Authentication auth) {
		System.out.println(auth);
		String role = auth.getAuthorities().toArray()[0].toString().substring(5);
		System.out.println(role);
		UserDto user = new UserDto();
		String username = auth.getName();
		if (role.equals("CUSTOMER")) {
			log.info("Getting details of a logged-in Customer: CustomerController");
			Customer customer = customerService.getCustomerByUsername(username);
			log.info("Got Customer details successfully: CustomerController");

			user.setUserId(customer.getCustomerId());
			user.setUserName(customer.getName());
			user.setUserRole(customer.getRole().substring(5));
			log.info("Returned Customer details: CustomerController");
		} else if (role.equals("DRIVER")) {
			Driver driver = driverService.viewDriverByUserName(username);
			user.setUserId(driver.getDriverId());
			user.setUserName(driver.getName());
			user.setUserRole(driver.getRole().substring(5));
		} else if (role.equals("ADMIN")) {
			Admin admin = adminService.getAdminDetailsByUserName(username);
			user.setUserId(admin.getAdminId());
			user.setUserName(admin.getName());
			user.setUserRole(admin.getRole().substring(5));
		}
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}
}

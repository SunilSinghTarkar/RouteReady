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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.routeready.model.Driver;
import com.routeready.service.DriverService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/drivers")
public class DriverController {
	@Autowired
	private DriverService driverService;
	
	@PostMapping("/")
	public ResponseEntity<Driver> createCustomer(@Valid @RequestBody Driver driver) {
		System.out.println(driver.toString());
		driver.setRole("Driver");
		driver.setActive(true);
		Driver driv = driverService.crateDriver(driver);
		return new ResponseEntity<Driver>(driv, HttpStatus.CREATED);
	}

	@GetMapping("/{driverId}")
	public ResponseEntity<Driver> getCustomer(@PathVariable Integer driverId) {
		Driver driv = driverService.getDriver(driverId);
		return new ResponseEntity<Driver>(driv, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Driver>> getCustomer() {
		List<Driver> list = driverService.getAllDriver();
		return new ResponseEntity<List<Driver>>(list, HttpStatus.OK);
	}

	@PatchMapping("/{driverId}")
	public ResponseEntity<Driver> updateCustomer(@RequestParam String address, @RequestParam String mobileNumber,
			@PathVariable Integer driverId) {
		Driver driv = driverService.updateDriver(address, mobileNumber, driverId);
		return new ResponseEntity<Driver>(driv, HttpStatus.OK);
	}

	@DeleteMapping("/{driverId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer driverId) {
		String str = driverService.deleteDriver(driverId);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
}

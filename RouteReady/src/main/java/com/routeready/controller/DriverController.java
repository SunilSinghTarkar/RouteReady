package com.routeready.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routeready.exception.RouteReadyException;
import com.routeready.model.Driver;
import com.routeready.model.DriverDto;
import com.routeready.model.TripBooking;
import com.routeready.service.DriverService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/drivers")
public class DriverController {

	@Autowired
	private DriverService driverService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/add")
	public ResponseEntity<Driver> addDriverHandler(@Valid @RequestBody Driver driver) {
		log.info("Class: DriverController, method: addDriverHandler started");
		driver.setRole("ROLE_DRIVER");
		driver.setPassword(passwordEncoder.encode(driver.getPassword()));
		Driver persistedDriver = driverService.insertDriver(driver);
		log.info("Class: DriverController, method: addDriverHandler returned " + persistedDriver);
		return new ResponseEntity<>(persistedDriver, HttpStatus.ACCEPTED);
	}

	@PatchMapping("/update/{driverId}")
	public ResponseEntity<Driver> updateDriverHandler(@Valid @RequestBody DriverDto driver,
			@PathVariable Integer driverId) {
		log.info("Class: DriverController, method: updateDriverHandler started");

		Driver updatedDriver = driverService.updateDriver(driver, driverId);
		log.info("Class: DriverController, method: updateDriverHandler returned " + updatedDriver);
		return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Driver> deleteDriverHandler(@PathVariable("id") Integer driverId) {
		log.info("Class: DriverController, method: deleteDriverHandler started");
		Driver deletedDriver = driverService.deleteDriver(driverId);
		log.info("Class: DriverController, method: deleteDriverHandler returned " + deletedDriver);
		return new ResponseEntity<>(deletedDriver, HttpStatus.OK);
	}

	

}

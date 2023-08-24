package com.routeready.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeready.exception.NotFoundException;
import com.routeready.exception.RouteReadyException;
import com.routeready.model.Customer;
import com.routeready.model.Driver;
import com.routeready.repository.DriverRepository;
@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository driverRepo;

	@Override
	public Driver crateDriver(Driver driver) {
		if (driver == null)
			throw new RouteReadyException("Please provide valid driver");
		Driver driv = driverRepo.save(driver);
		return driv;
	}

	@Override
	public Driver getDriver(Integer driverId) {
		Driver driver = driverRepo.findById(driverId)
				.orElseThrow(() -> new NotFoundException("driver not found with given Id; " + driverId));
		
		if (!driver.isActive())
			throw new NotFoundException("driver not found with given Id; " + driverId);
		return driver;
	}

	@Override
	public List<Driver> getAllDriver() {
		List<Driver> driverList = driverRepo.findAll();
		if (driverList.isEmpty())
			throw new NotFoundException("driver not found!");
		return driverList;
	}

	@Override
	public Driver updateDriver(String address, String mobileNumber, Integer driverId) {
		Driver driver = driverRepo.findById(driverId)
				.orElseThrow(() -> new NotFoundException("driver not found with given Id; " + driverId));
		
		driver.setAddress(address);
		driver.setMobileNumber(mobileNumber);

		return driverRepo.save(driver);
		
	}

	@Override
	public String deleteDriver(Integer driverId) {
		Driver driver = driverRepo.findById(driverId)
				.orElseThrow(() -> new NotFoundException("driver not found with given Id; " + driverId));
		
		driver.setActive(false);
		driverRepo.save(driver);
		return "driver deleted succesfully";
		
	}

}

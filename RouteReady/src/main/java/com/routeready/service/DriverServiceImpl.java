package com.routeready.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeready.model.Driver;
import com.routeready.repository.DriverRepository;
@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository driverRepo;

	@Override
	public Driver crateDriver(Driver driver) {
		
		return null;
	}

	@Override
	public Driver getDriver(Integer driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Driver> getAllDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver updateDriver(String address, String mobileNumber, Integer driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDriver(Integer driverId) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.routeready.service;

import java.util.List;

import com.routeready.model.Driver;

public interface DriverService {
	public Driver crateDriver(Driver driver);
	  public Driver getDriver(Integer driverId);
	  public List<Driver> getAllDriver( );
	  public Driver updateDriver(String address,String mobileNumber,Integer driverId);
	  public String deleteDriver(Integer driverId);
}

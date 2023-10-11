package com.routeready.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeready.exception.NotFoundException;
import com.routeready.exception.RouteReadyException;
import com.routeready.model.Driver;
import com.routeready.model.DriverDto;
import com.routeready.repository.DriverRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverRepository driverRepository;

	/**
	 * @param driver
	 * @return Driver Take a driver object to be persisted and return persisted
	 *         driver;
	 */
	@Override
	public Driver insertDriver(Driver driver) {
		log.info("Class: DriverServiceImpl, method: insertDriver started ");

		// checking for null;
		if (driver == null)
			throw new RouteReadyException("null value");

		// checking for already existing username;
		Optional<Driver> opt = driverRepository.findByUserName(driver.getUserName());
		if (opt.isPresent())
			throw new RouteReadyException("This username already exists, please provide another username");

		// checking for already existing mobile number;
		Optional<Driver> opt1 = driverRepository.findByMobileNumber(driver.getMobileNumber());
		if (opt1.isPresent())
			throw new RouteReadyException("This mobile number already exists, please provide another mobile number");

		// checking for already existing email;
		Optional<Driver> opt2 = driverRepository.findByEmail(driver.getEmail());
		if (opt2.isPresent())
			throw new RouteReadyException("This email already exists, please provide another email");

		// persisting the driver object;
		Driver persistedDriver = driverRepository.save(driver);
		log.info("Class: DriverServiceImpl, method: insertDriver returned " + persistedDriver);
		return persistedDriver;
	}

	/**
	 * @param driver
	 * @return Driver Take a driver object to be updated, return updated driver;
	 */
	@Override
	public Driver updateDriver(DriverDto driver, Integer driverId) {
		log.info("Class: DriverServiceImpl, method: updateDriver started ");

		// checking for null;
		if (driver == null)throw new RouteReadyException("null value");

		Driver drivr = driverRepository.findById(driverId)
				.orElseThrow(() -> new RouteReadyException("This Driver does not exists"));
		drivr.setName(driver.getName());
		drivr.setMobileNumber(driver.getMobileNumber());
		drivr.setEmail(driver.getEmail());
		drivr.setAddress(driver.getAddress());
		drivr.setLicenceNumber(drivr.getLicenceNumber());

		log.info("Class: DriverServiceImpl, method: updateDriver Assigned " + drivr + " " + drivr.getLicenceNumber());
		Driver updatedDriver = driverRepository.save(drivr);
		log.info("Class: DriverServiceImpl, method: updateDriver returned " + updatedDriver);
		return updatedDriver;
	}

	/**
	 * @param driverId
	 * @return Driver Take driver id of Integet type, return deletedDriver;
	 */
	@Override
	public Driver deleteDriver(Integer driverId) {
		log.info("Class: DriverServiceImpl, method: deleteDriver started ");

		Driver driver = driverRepository.findById(driverId)
				.orElseThrow(() -> new NotFoundException("Driver with id: " + driverId + "does not exist."));
		driver.setIsDeleted(true);
		driverRepository.save(driver);
		log.info("Class: DriverServiceImpl, method: deleteDriver returned deleted driver");
		return driver;
	}

	/**
	 * @param driverId
	 * @return Driver Take driver id of integer type, returns driver;
	 */
	@Override
	public Driver viewDriver(Integer driverId) {
		log.info("Class: DriverServiceImpl, method: viewDriver started ");
		Driver driver = driverRepository.findById(driverId)
				.orElseThrow(() -> new NotFoundException("driver with id: " + driverId + " not found"));
		log.info("Class: DriverServiceImpl, method: viewDriver returned " + driver);
		return driver;
	}

	/**
	 * @param userName
	 * @return Driver
	 */
	@Override
	public Driver viewDriverByUserName(String userName) {
		Driver driver = driverRepository.findByUserName(userName)
				.orElseThrow(() -> new NotFoundException(" Driver not found"));
		return driver;
	}

}

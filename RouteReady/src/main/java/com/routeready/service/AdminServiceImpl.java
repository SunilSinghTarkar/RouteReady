package com.routeready.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeready.exception.DataNotFoundException;
import com.routeready.exception.InvalidInputException;
import com.routeready.exception.NotFoundException;
import com.routeready.model.Admin;
import com.routeready.model.Cab;
import com.routeready.model.Customer;
import com.routeready.model.Driver;
import com.routeready.model.Query;
import com.routeready.model.TripBooking;
import com.routeready.repository.AdminRepository;
import com.routeready.repository.CabRepository;
import com.routeready.repository.CustomerRepository;
import com.routeready.repository.DriverRepository;
import com.routeready.repository.QueryRepository;
import com.routeready.repository.TripBookingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private TripBookingRepository tripBookingRepo;
	@Autowired
	private DriverRepository driverRepo;
	@Autowired
	private CabRepository cabRepo;
	@Autowired
	private QueryRepository queryRepo;

	@Override
	public Admin insertAdmin(Admin admin) throws InvalidInputException {

		if (admin == null) {
			log.warn("Admin data is null.");
			throw new InvalidInputException("Data of admin is null.");
		}
		Optional<Admin> opt = adminRepo.findByUserName(admin.getUserName());
		Optional<Customer> opt2 = customerRepo.findByuserName(admin.getUserName());
		Optional<Driver> opt3 = driverRepo.findByUserName(admin.getUserName());
		if (opt.isPresent()||opt2.isPresent()||opt3.isPresent()) {
			log.warn("Account with this userName is already present in database.");
			throw new InvalidInputException("Account with this userName is already present is database.");
		}
		Admin savedAdmin = adminRepo.save(admin);
		log.info("Admin is added successfully.");
		return savedAdmin;
	}

	@Override
	public Admin updateAdmin(Integer adminId, Admin admin) throws InvalidInputException {
		if (admin == null) {
			log.warn("Admin data is null.");
			throw new InvalidInputException("Data of admin is null.");
		}
		Optional<Admin> opt = adminRepo.findById(adminId);
		if (opt.isEmpty()) {
			log.warn("Admin with this userid is not present in database.");
			throw new InvalidInputException("Admin with this userid is not present in database.");
		}
		Admin adminPresent = opt.get();

		adminPresent.setUserName(admin.getUserName());
		adminPresent.setPassword(admin.getPassword());
		adminPresent.setAddress(admin.getAddress());
		adminPresent.setMobileNumber(admin.getMobileNumber());
		adminPresent.setEmail(admin.getEmail());

		log.info("Admin is updated successfully.");
		return adminPresent;
	}

	@Override
	public Admin deleteAdmin(Integer adminId) throws NotFoundException {
		Optional<Admin> opt = adminRepo.findById(adminId);
		if (opt.isEmpty()) {
			log.warn("Admin with this userid is not present in database.");
			throw new InvalidInputException("Admin with this userid is not present in database.");
		}
		adminRepo.deleteById(adminId);
		log.info("Admin is deleted successfully.");
		return opt.get();
	}

	@Override
	public List<TripBooking> getAllTripsOfCustomer(Integer customerId) throws NotFoundException {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isEmpty()) {
			log.warn("Customer with this customerId is not present in database.");
			throw new InvalidInputException("Customer with this customerId is not present in database.");
		}
		List<TripBooking> bookings = opt.get().getTripBookings();
		log.info("All trips of customer are successfully fatched.");
		return bookings;
	}

	@Override
	public List<TripBooking> getTripsCabWise(Integer desiredCabId) throws DataNotFoundException {
		List<TripBooking> trips = tripBookingRepo.findAllTripBooking();

		if (trips.isEmpty()) {
			log.warn("Trips with this cabId is not present in database.");
			throw new DataNotFoundException("No trips found.");
		}
		List<TripBooking> tripsCabWise = new ArrayList<>();
		for (TripBooking trip : trips) {
			if (trip.getDriver().getCab().getCabId() == desiredCabId) {
				tripsCabWise.add(trip);
			}
		}
		if (tripsCabWise.isEmpty()) {
			log.warn("No trips found for the specified cab.");
			throw new DataNotFoundException("No trips found for the specified cab.");
		}
		log.info("All trips with specified cab are fatched.");
		return tripsCabWise;
	}

	@Override
	public List<TripBooking> getTripsCustomerWise(Integer customerId) throws DataNotFoundException {
		List<TripBooking> trips = tripBookingRepo.findAllTripBooking();

		if (trips.isEmpty()) {
			log.warn("Trips with this customerId is not present in database.");
			throw new DataNotFoundException("No trips found.");
		}
		List<TripBooking> tripsCustomerWise = new ArrayList<>();
		for (TripBooking trip : trips) {
			if (trip.getCustomer().getCustomerId() == customerId) {
				tripsCustomerWise.add(trip);
			}
		}
		if (tripsCustomerWise.isEmpty()) {
			log.warn("No trips found for the specified customer.");
			throw new DataNotFoundException("No trips found for the specified cab.");
		}
		log.info("All trips with specified customer are fatched.");
		return tripsCustomerWise;
	}

	@Override
	public List<TripBooking> getTripsDateWise(LocalDateTime dateTime) throws DataNotFoundException {
		List<TripBooking> trips = tripBookingRepo.findAllTripBooking();

		if (trips.isEmpty()) {
			log.warn("Trips are not present in database.");
			throw new DataNotFoundException("No trips found.");
		}
		List<TripBooking> tripsDateWise = new ArrayList<>();
		for (TripBooking trip : trips) {
			if (trip.getFromDateTime().isBefore(dateTime) && trip.getToDateTime().isAfter(dateTime)) {
				tripsDateWise.add(trip);
			}
		}
		if (tripsDateWise.isEmpty()) {
			log.warn("No trips found for the specified customer.");
			throw new DataNotFoundException("No trips found for the specified cab.");
		}
		log.info("All trips with specified customer are fatched.");
		return tripsDateWise;
	}

	@Override
	public List<TripBooking> getAllTripsForDays(Integer customerId, LocalDateTime fromDate, LocalDateTime toDate)
			throws DataNotFoundException {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if (opt.isEmpty()) {
			log.warn("Customer with this customerId is not present in database.");
			throw new InvalidInputException("Customer with this customerId is not present in database.");
		}
		List<TripBooking> bookings = opt.get().getTripBookings();
		List<TripBooking> required = new ArrayList<>();
		for (TripBooking booking : bookings) {
			if (booking.getFromDateTime() == fromDate && booking.getToDateTime() == toDate) {
				required.add(booking);
			}
		}
		log.info("All trips are successfully fatched.");
		return required;
	}

	@Override
	public String assignCabToDriver(Integer driverId, Integer cabId) {
		log.info("Inside assignCabToDriver Method");

		Driver driver = driverRepo.findById(driverId)
				.orElseThrow(() -> new NotFoundException("Invalid Driver ID: " + driverId));
		Cab cab = cabRepo.findById(cabId).orElseThrow(() -> new NotFoundException("Invalid Cab ID: " + cabId));
		cab.setDriver(driver);
		driver.setCab(cab);
		Driver savedDriver = driverRepo.save(driver);
//          System.out.println(savedDriver);
		return "Cab Assigned To Driver";
	}

	@Override
	public List<Query> getAllQuery() {
		List<Query> list = queryRepo.findAll();
		if (list.isEmpty())
			throw new NotFoundException(" Query Not Found!");
		return list;
	}

	@Override
	public Query insertQuery(Query query) {
		query.setOpen(true);

		return queryRepo.save(query);
	}

	@Override
	public Admin getAdminDetailsByUserName(String name) {
		Admin admin = adminRepo.findByUserName(name)
				.orElseThrow(() -> new NotFoundException("Invalid userName try again!!"));
		return admin;
	}

}

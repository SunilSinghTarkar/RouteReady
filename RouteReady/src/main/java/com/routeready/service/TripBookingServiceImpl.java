package com.routeready.service;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.routeready.exception.NotFoundException;
import com.routeready.exception.RouteReadyException;
import com.routeready.model.Cab;
import com.routeready.model.Customer;
import com.routeready.model.Driver;
import com.routeready.model.TripBooking;
import com.routeready.model.TripBookingDto;
import com.routeready.repository.CustomerRepository;
import com.routeready.repository.DriverRepository;
import com.routeready.repository.TripBookingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TripBookingServiceImpl implements TripBookingService {

	private static final Logger logger = LoggerFactory.getLogger(TripBookingServiceImpl.class);
	@Autowired
	private TripBookingRepository tripBookingRepository;

	@Autowired
	private DriverRepository driverRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public TripBooking insertTripBooking(TripBookingDto tripBooking, Integer customerId) throws RouteReadyException {
		logger.info("Inserting trip booking: {}", tripBooking);

		try {
			Optional<Customer> customer = customerRepository.findById(customerId);

			if (customer.isPresent()) {
				Customer cust = customer.get();
				TripBooking tripB = new TripBooking();

				tripB.setFromLocation(tripBooking.getFromLocation());
				tripB.setToLocation(tripBooking.getToLocation());
				tripB.setFromDateTime(tripBooking.getFromDateTime());
				tripB.setToDateTime(tripBooking.getToDateTime());
				tripB.setDistanceInKm(tripBooking.getDistanceInKm());
				tripB.setCustomer(cust);

				List<Driver> driverlist = driverRepository.findAll();
				int n=driverlist.size();
				Driver driver = null;
				for (int i = 11; i < n; i++) {
					
					if (driverlist.get(i).getIsAvailable()&&driverlist.get(i).getCab()!=null) {
						driver = driverlist.get(i);
					    if(i<n-1)break;
					} 
					else if(i>=n-2) {
					for(int j=11;j<n;j++) {
					 	driverlist.get(j).setIsAvailable(true);
					 	driverRepository.save(driverlist.get(j));
					}
					}
				}

				if (driver == null)
					throw new RouteReadyException("No Driver Available at the moment");

				tripB.setDriver(driver);
				Cab cab = driver.getCab();
				if (cab == null)
					throw new RouteReadyException("Cab Not assign to the Driver");

				double ratePerKm = cab.getPerKmRate();
				double totalBill = tripBooking.getDistanceInKm() * ratePerKm;
				tripB.setBill(totalBill);
				System.out.println(totalBill + "totalBill");
				driver.getTripBookings().add(tripB);
				driver.setIsAvailable(false);

				cust.getTripBookings().add(tripB);

				tripBookingRepository.save(tripB);

				return tripB;

			} else {
				throw new RouteReadyException("Customer not found with id " + customerId);
			}
		} catch (Exception e) {
			logger.error("Error occurred while inserting trip booking", e);
			throw new RouteReadyException("Failed to insert trip booking");
		}
	}

	@Override
	public TripBooking updateTripBooking(TripBookingDto tripBooking) throws RouteReadyException {
		logger.info("Updating trip booking: {}", tripBooking);

		try {
			TripBooking trip = tripBookingRepository.findById(tripBooking.getTripId()).orElseThrow(
					() -> new NotFoundException("Trip not found with given Id: " + tripBooking.getTripId()));
			trip.setFromLocation(tripBooking.getFromLocation());
			trip.setToLocation(tripBooking.getToLocation());
			trip.setFromDateTime(tripBooking.getFromDateTime());
			trip.setToDateTime(tripBooking.getToDateTime());
			trip.setDistanceInKm(tripBooking.getDistanceInKm());
			double totalBill = trip.getDriver().getCab().getPerKmRate() * tripBooking.getDistanceInKm();
			System.out.println(trip.getDistanceInKm() + " totalBill " + totalBill);
			trip.setBill(totalBill);
			return tripBookingRepository.save(trip);
		} catch (Exception e) {
			logger.error("Error occurred while updating trip booking", e);
			throw new RouteReadyException("Failed to update trip booking");
		}
	}

	@Override
	public String deleteTripBooking(Integer tripBookingId) throws RouteReadyException {
		logger.info("Deleting trip booking with ID: {}", tripBookingId);

		try {
			TripBooking tripBooking = tripBookingRepository.findById(tripBookingId)
					.orElseThrow(() -> new RouteReadyException("Trip booking not found with ID: " + tripBookingId));

			tripBookingRepository.delete(tripBooking);
			return "Trip deleted succesfully";
		} catch (Exception e) {
			logger.error("Error occurred while deleting trip booking", e);
			throw new RouteReadyException("Failed to delete trip booking");
		}
	}

	@Override
	public List<TripBooking> viewAllTripsCustomer(Integer customerId) throws RouteReadyException {
		logger.info("Retrieving all trip bookings for customer with ID: {}", customerId);
		try {
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new RouteReadyException("Customer not found with ID: " + customerId));

			return tripBookingRepository.findAllByCustomer(customer);
		} catch (Exception e) {
			logger.error("Error occurred while retrieving trip bookings for customer with ID: {}", customerId, e);
			throw new RouteReadyException("Failed to retrieve trip bookings for customer");
		}
	}
	@Override
	public List<TripBooking> viewAllTripsDriver(Integer driverId) throws RouteReadyException {
		logger.info("Retrieving all trip bookings for customer with ID: {}", driverId);
		try {
			Driver driver = driverRepository.findById(driverId)
					.orElseThrow(() -> new RouteReadyException("Driver not found with ID: " + driverId));
			
			return tripBookingRepository.findAllByDriver(driver);
		} catch (Exception e) {
			logger.error("Error occurred while retrieving trip bookings for Driver with ID: {}", driverId, e);
			throw new RouteReadyException("Failed to retrieve trip bookings for customer");
		}
	}

	@Override
	public List<TripBooking> getAllTripBooking(Integer pageNo,Integer pageSize) throws RouteReadyException {
		logger.info("Retrieving all trip bookings");
		try {
			Pageable pageable = PageRequest.of(pageNo,pageSize);
			Page<TripBooking> page=tripBookingRepository.findAll(pageable);
			List<TripBooking> tripList = page.getContent();
			return tripList;
		} catch (Exception e) {
			logger.error("Error occurred while retrieving trip bookings", e);
			throw new RouteReadyException("Failed to retrieve trip bookings");
		}
	}
}

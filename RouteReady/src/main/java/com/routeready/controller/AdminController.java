package com.routeready.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.routeready.exception.RouteReadyException;
import com.routeready.model.Admin;
import com.routeready.model.Query;
import com.routeready.model.TripBooking;
import com.routeready.model.UserDto;
import com.routeready.service.AdminService;
import com.routeready.service.TripBookingService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private TripBookingService tripBookingService;
	@Autowired
	private PasswordEncoder encoder;

	// Add a new Admin
	@PostMapping("/add")
	public ResponseEntity<Admin> insertAdminController(@Valid @RequestBody Admin admin) {
		try {
			log.info("Try to insert new Admin : AdminController");
			admin.setRole("ROLE_ADMIN");
			admin.setPassword(encoder.encode(admin.getPassword()));
			Admin insertedAdmin = adminService.insertAdmin(admin);
			log.info("Admin inserted successfully : AdminController");
			return ResponseEntity.status(HttpStatus.CREATED).body(insertedAdmin);
		} catch (RouteReadyException ex) {
			log.warn("Admin insertion failed : AdminController");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// Update Admin Details
	@PutMapping("/{adminId}")
	public ResponseEntity<Admin> updateAdminDetails(@PathVariable Integer adminId, @Valid @RequestBody Admin admin) {
		try {
			log.info("Try to update Admin : AdminController");
			Admin updatedAdmin = adminService.updateAdmin(adminId, admin);
			log.info("Admin updated successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(updatedAdmin);
		} catch (RouteReadyException ex) {
			log.warn("Admin updation failed : AdminController");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// Delete an Admin
	@DeleteMapping("/{adminId}")
	public ResponseEntity<Admin> deleteAdminController(@PathVariable Integer adminId) {
		try {
			log.info("Try to delete Admin : AdminController");
			Admin deletedAdmin = adminService.deleteAdmin(adminId);
			log.info("Admin deleted successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(deletedAdmin);
		} catch (RouteReadyException ex) {
			log.warn("Admin deletion failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Get all trips of a specific customer
	@GetMapping("/customer/allTrips/{customerId}")
	public ResponseEntity<List<TripBooking>> getAllTripsOfCustomerController(@PathVariable Integer customerId) {
		try {
			log.info("Try to get all trips of a customer : AdminController");
			List<TripBooking> tripBookings = adminService.getAllTripsOfCustomer(customerId);
			log.info("All trips retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(tripBookings);
		} catch (RouteReadyException ex) {
			log.warn("Getting trips of a customer failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Get all trips associated with a specific cab
	@GetMapping("/cab/{cabId}")
	public ResponseEntity<List<TripBooking>> getTripsCabWiseController(@PathVariable Integer cabId) {
		try {
			log.info("Try to get all trips cab wise : AdminController");
			List<TripBooking> tripBookings = adminService.getTripsCabWise(cabId);
			log.info("All trips retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(tripBookings);
		} catch (RouteReadyException ex) {
			log.warn("Getting trips cab wise failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Get all trips associated with a specific customer
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<TripBooking>> getTripsCustomerWiseController(@PathVariable Integer customerId) {
		try {
			log.info("Try to get all trips customer wise : AdminController");
			List<TripBooking> tripBookings = adminService.getTripsCustomerWise(customerId);
			log.info("All trips retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(tripBookings);
		} catch (RouteReadyException ex) {
			log.warn("Getting trips customer wise failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Get all trips for a specific date and time
	@GetMapping("/date/{dateTime}")
	public ResponseEntity<List<TripBooking>> getTripsDateWiseController(@PathVariable LocalDateTime dateTime) {
		try {
			log.info("Try to get all trips date wise : AdminController");
			List<TripBooking> tripBookings = adminService.getTripsDateWise(dateTime);
			log.info("All trips retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(tripBookings);
		} catch (RouteReadyException ex) {
			log.warn("Getting trips date wise failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Get all trips for a specific date range
	@GetMapping("/forDays/{customerId}/{fromDate}/{toDate}")
	public ResponseEntity<List<TripBooking>> getAllTripsForDaysController(@PathVariable Integer customerId,
			LocalDateTime fromDate, LocalDateTime toDate) {
		try {
			log.info("Try to get all trips for days : AdminController");
			List<TripBooking> tripBookings = adminService.getAllTripsForDays(customerId, fromDate, toDate);
			log.info("All trips retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(tripBookings);
		} catch (RouteReadyException ex) {
			log.warn("Getting trips for days failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Assign a cab to a driver
	@PostMapping("/assignCabToDriver/{driverId}/{cabId}")
	public ResponseEntity<String> assignCabToDriver(@PathVariable Integer driverId, @PathVariable Integer cabId) {
		String str = adminService.assignCabToDriver(driverId, cabId);
		return new ResponseEntity<>(str, HttpStatus.ACCEPTED);
	}

	// Get all queries
	@GetMapping("/querys")
	public ResponseEntity<List<Query>> getAllQuery() {
		try {
			log.info("Try to get all Queries of customers : AdminController");
			List<Query> queryList = adminService.getAllQuery();
			log.info("All queries retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(queryList);
		} catch (RouteReadyException ex) {
			log.warn("Getting queries of customers failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// Insert a new query
	@PostMapping("/querys")
	public ResponseEntity<Query> insertQuery(@Valid @RequestBody Query query) {
		try {
			log.info("Try to insert a new Query : AdminController");
			Query insertedQuery = adminService.insertQuery(query);
			log.info("Query inserted successfully : AdminController");
			return ResponseEntity.status(HttpStatus.CREATED).body(insertedQuery);
		} catch (RouteReadyException ex) {
			log.warn("Query insertion failed : AdminController");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// Get all trip bookings with pagination
	@GetMapping("/alltripbookings")
	public ResponseEntity<List<TripBooking>> getAllTrips(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		try {
			log.info("Try to get all trips with pagination : AdminController");
			List<TripBooking> tripBookings = tripBookingService.getAllTripBooking(pageNo, pageSize);
			log.info("All trips retrieved successfully : AdminController");
			return ResponseEntity.status(HttpStatus.OK).body(tripBookings);
		} catch (RouteReadyException ex) {
			log.warn("Getting trips failed : AdminController");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}

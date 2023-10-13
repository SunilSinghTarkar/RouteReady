package com.routeready.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routeready.exception.RouteReadyException;
import com.routeready.model.TripBooking;
import com.routeready.model.TripBookingDto;
import com.routeready.service.TripBookingService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/tripbookings")
public class TripBookingController {

    @Autowired
    private TripBookingService tripBookingService;

    // Create a new trip booking for a customer
    @PostMapping("/{customerId}")
    public ResponseEntity<TripBooking> insertTripBooking(@Valid @RequestBody TripBookingDto tripBooking,
            @PathVariable("customerId") Integer customerId) {
        try {
            TripBooking createdTripBooking = tripBookingService.insertTripBooking(tripBooking, customerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTripBooking);
        } catch (RouteReadyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Update an existing trip booking
    @PutMapping("/update")
    public ResponseEntity<TripBooking> updateTripBooking(@Valid @RequestBody TripBookingDto tripBooking) {
        TripBooking trip = tripBookingService.updateTripBooking(tripBooking);
        return new ResponseEntity<TripBooking>(trip, HttpStatus.OK);
    }

    // Delete a trip booking by ID
    @DeleteMapping("/{tripBookingId}")
    public ResponseEntity<String> deleteTripBooking(@PathVariable("tripBookingId") int tripBookingId) {
        String str = tripBookingService.deleteTripBooking(tripBookingId);
        return new ResponseEntity<String>(str, HttpStatus.OK);
    }

    // Get a list of all trips for a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TripBooking>> viewAllTripsCustomer(@PathVariable("customerId") int customerId) {
        try {
            List<TripBooking> tripBookings = tripBookingService.viewAllTripsCustomer(customerId);
            return ResponseEntity.ok(tripBookings);
        } catch (RouteReadyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get a list of all trips for a specific driver
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<TripBooking>> viewAllTripsDriver(@PathVariable("driverId") int driverId) {
        try {
            List<TripBooking> tripBookings = tripBookingService.viewAllTripsDriver(driverId);
            return ResponseEntity.ok(tripBookings);
        } catch (RouteReadyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

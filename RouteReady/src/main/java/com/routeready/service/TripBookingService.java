package com.routeready.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.routeready.exception.RouteReadyException;
import com.routeready.model.TripBooking;
import com.routeready.model.TripBookingDto;

public interface TripBookingService {
	public TripBooking insertTripBooking(TripBookingDto tripBooking, Integer customerId) throws RouteReadyException;

	public TripBooking updateTripBooking(TripBookingDto tripBookingDto) throws RouteReadyException;

	public String deleteTripBooking(Integer tripBookingId) throws RouteReadyException;

	public List<TripBooking> viewAllTripsCustomer(Integer customerld) throws RouteReadyException;

	public List<TripBooking> getAllTripBooking(Integer pageNo, Integer pageSize) throws RouteReadyException;

	public List<TripBooking> viewAllTripsDriver(Integer driverId) throws RouteReadyException;

}

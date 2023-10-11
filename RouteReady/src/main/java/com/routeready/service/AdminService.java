package com.routeready.service;

import java.time.LocalDateTime;
import java.util.List;

import com.routeready.exception.DataNotFoundException;
import com.routeready.exception.InvalidInputException;
import com.routeready.exception.NotFoundException;
import com.routeready.model.Admin;
import com.routeready.model.Query;
import com.routeready.model.TripBooking;

public interface AdminService {

	public Admin insertAdmin(Admin admin) throws InvalidInputException;

	public Admin updateAdmin(Integer adminId, Admin admin) throws InvalidInputException;

	public Admin deleteAdmin(Integer adminId) throws NotFoundException;

	public List<TripBooking> getAllTripsOfCustomer(Integer customerId) throws NotFoundException;

	public List<TripBooking> getTripsCabWise(Integer desiredCabId) throws DataNotFoundException;

	public List<TripBooking> getTripsCustomerWise(Integer customerId) throws DataNotFoundException;

	public List<TripBooking> getTripsDateWise(LocalDateTime dateTime) throws DataNotFoundException;

	public List<TripBooking> getAllTripsForDays(Integer customerId, LocalDateTime fromDate, LocalDateTime toDate)
			throws DataNotFoundException;

	public String assignCabToDriver(Integer driverId, Integer cabId);

	public List<Query> getAllQuery();

	public Query insertQuery(Query query);

	public Admin getAdminDetailsByUserName(String name);

}

package com.routeready.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.routeready.model.Customer;
import com.routeready.model.Driver;
import com.routeready.model.TripBooking;

@Repository
public interface TripBookingRepository extends JpaRepository<TripBooking, Integer> {

    @Query("SELECT tb FROM TripBooking tb")
    public List<TripBooking> findAllTripBooking();
    List<TripBooking> findAllByCustomer(Customer customer);
    List<TripBooking> findAllByDriver(Driver driver);
    
}

package com.routeready.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routeready.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

}

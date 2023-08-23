package com.routeready.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Driver driver;
	@OneToOne
	private Cab cab;
	private String pickupLocation;
	private String dropLocation;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String status;
	private double distance;
	private double totalPrice;

}

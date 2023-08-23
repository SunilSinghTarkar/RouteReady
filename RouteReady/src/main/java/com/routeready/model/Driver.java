package com.routeready.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int driverId;
	@Embedded
	private User user;
	@Size(min = 5, max = 5, message = "License number must be 5 characters long")
	private String licenceNo;
	@OneToOne(mappedBy = "driver")
	private Cab cab;
	private double rating;
	@OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Booking> bookingHistory = new ArrayList<>();

}

package com.routeready.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cab {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cabId;
	
	private String carType;
	@NotNull(message = "Please provide Price Per Km!")
	private double pricePkm;
	@OneToOne
	private Driver driver;
}

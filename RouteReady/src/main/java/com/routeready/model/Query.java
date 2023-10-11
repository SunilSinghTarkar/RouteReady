package com.routeready.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Query {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int queryId;
	private String firstName;
	private String lastName;
	private String state;
	private String message;
	private boolean isOpen;
}

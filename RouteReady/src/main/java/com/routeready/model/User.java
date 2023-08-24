package com.routeready.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@NotBlank(message = "Please provide a valid userName")
	private String userName;
	@Pattern(regexp = "[a-z0-9._]+@[a-z0-9]+\\.[a-z]{2,3}", flags = Flag.CASE_INSENSITIVE, message = "Please provide a valid Email!")
	private String email;
	@Size(min = 5, max = 50, message = "Please provide complete address")
	private String address;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Pattern(regexp = "^[6-9][0-9]{9}", message = "Please provide a valid mobile number!")
	private String mobileNumber;
//	@JsonIgnore
	private String role;
//	@JsonIgnore
	private boolean isActive;

}

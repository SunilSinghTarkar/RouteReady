package com.routeready.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
	@NotNull(message = "Name can not be null")
	@NotBlank(message = "Name can not be Blank")
	@Size(min = 2, max = 25, message = "Name length Between 2 and 25 character")
	private String name;

	@NotNull(message = "address can not be null")
	@NotBlank(message = "Address can not be Blank")
	private String address;
	
	@NotNull(message = "Mobile Number can not be null")
	@NotBlank(message = "Mobile Number can not be Blank")
	@Pattern(regexp = "^[6-9][0-9]{9}", message = "Mobile number should start with 6-9 and 10 digit Only")
	@Column(unique = true)
	private String mobileNumber;
	
	@Email
	@NotNull(message = "Email can not be null")
	@Column(unique = true)
	private String email;

}

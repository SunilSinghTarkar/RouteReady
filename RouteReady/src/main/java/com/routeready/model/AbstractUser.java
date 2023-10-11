package com.routeready.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractUser {

    @NotNull(message = "Name can not be null")
    @NotBlank(message = "Name can not be Blank")
    @Size(min = 2,max = 25,message = "Name length Between 2 and 25 character")
    private String name;

    @NotNull(message = "User Name can not be null testing here")
    @NotBlank(message = "Username can not be Blank")
    @Size(min = 3,max = 15,message = "Username length Between 3 and 10 character")
    @Column(unique = true)
//    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9]{2,}",message = "Username should contain Uppercase and lowercase and numeric value")
    private String userName;
    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be Blank")
    @Size(min = 4,message = "Password length more than 4 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull(message = "address can not be null")
    @NotBlank(message = "Address can not be Blank")
    private String address;
    @NotNull(message = "Mobile Number can not be null")
    @NotBlank(message = "Mobile Number can not be Blank")
    @Pattern(regexp = "^[6-9][0-9]{9}",message = "Mobile number should start with 6-9 and 10 digit Only")
    @Column(unique = true)
    private String mobileNumber;
    @Email
    @NotNull(message = "Email can not be null")
    @Column(unique = true)
    private String email;

    private String role;

    private Boolean isDeleted = false;
}

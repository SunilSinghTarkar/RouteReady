package com.routeready.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends AbstractUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;

    @NotNull(message = "Licence can not be null")
    @NotBlank(message = "Licence can not be Blank")
//    @Pattern(regexp = "^[A-Z]{2}[0-9]", message = "Invalid driving license number. Please enter a valid driving license number.")
    private String licenceNumber;

    private Float rating;
    private Boolean isAvailable=true;

    //RelationShip Start
    
//    @JsonIgnore
    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    private Cab cab;

    @JsonIgnore
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookings = new ArrayList<>();

	

    
    
    

}

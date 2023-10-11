package com.routeready.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripBookingId;
    @NotNull(message = "Starting Location can not be null")
    @NotBlank(message = "Starting Location can not be Blank")
    private String fromLocation;
    @NotNull(message = "To Location can not be null")
    @NotBlank(message = "Ending Location can not be Blank")
    private String toLocation;

    @NotNull(message = "Starting Date and Time can not be null")
    private LocalDateTime fromDateTime;

    @NotNull(message = "Ending Date and Time can not be null")
    private LocalDateTime toDateTime;

    private Boolean status;

    @NotNull(message = "Distance can not be null")
    private double distanceInKm;

    private double bill;

   
//    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Driver driver;



}

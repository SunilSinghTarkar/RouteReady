package com.routeready.service;

import com.routeready.model.Driver;
import com.routeready.model.DriverDto;

public interface DriverService {

    public Driver insertDriver(Driver driver);

    public Driver updateDriver(DriverDto driver,Integer driverId);

    public Driver deleteDriver(Integer driverId);


    public Driver viewDriver(Integer driverId);

    public Driver viewDriverByUserName(String userName);

}

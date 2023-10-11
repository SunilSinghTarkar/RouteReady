package com.routeready.service;

import java.util.List;

import com.routeready.model.Cab;

public interface CabService {

    public Cab insertCab(Cab cab);

    public Cab updateCab(Cab cab);

    public Cab deleteCab(Integer cabId);

    public List<Cab> viewCabsOfType(String carType);

    public Integer countCabsOfType(String carType);
}

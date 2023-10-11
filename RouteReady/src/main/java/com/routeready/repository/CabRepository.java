package com.routeready.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.routeready.model.Cab;

import java.util.List;

public interface CabRepository extends JpaRepository<Cab, Integer> , PagingAndSortingRepository<Cab, Integer> {

    public List<Cab> findByCarType(String carType);

    public Integer countByCarType(String carType);

}

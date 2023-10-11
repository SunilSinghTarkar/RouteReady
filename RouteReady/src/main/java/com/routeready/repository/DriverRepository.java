package com.routeready.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.routeready.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer>, PagingAndSortingRepository<Driver, Integer> {

	public Optional<Driver> findByUserName(String userName);

	public Optional<Driver> findByMobileNumber(String mobileNumber);

	public Optional<Driver> findByEmail(String email);

	public List<Driver> findByRatingGreaterThanEqual(Float rating);

}

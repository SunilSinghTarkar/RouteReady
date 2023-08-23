package com.routeready.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routeready.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

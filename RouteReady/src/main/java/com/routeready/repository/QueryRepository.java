package com.routeready.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routeready.model.Query;

public interface QueryRepository extends JpaRepository<Query, Integer>{

}

package com.javacodegeeks.examples.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javacodegeeks.examples.jpa.model.BaseModel;

public interface BaseDAOInt<T extends BaseModel> extends JpaRepository<T, Long> {

}

package com.javacodegeeks.examples.jpa.service;

import org.springframework.stereotype.Service;

import com.javacodegeeks.examples.jpa.model.Country;
import com.javacodegeeks.examples.jpa.repository.BaseDAOInt;

@Service
public class CountryServiceImpl<T extends Country, D extends BaseDAOInt<Country>> extends BaseServiceImpl<Country>
		implements CountryServiceInt<Country>

{

}

package com.javacodegeeks.examples.jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacodegeeks.examples.jpa.model.Country;
import com.javacodegeeks.examples.jpa.service.CountryServiceInt;
import com.javacodegeeks.examples.jpa.service.StateServiceInt;

@RestController
@RequestMapping("/country")
public class CountryCtl extends BaseCtl<Country,CountryServiceInt<Country>> {

}
 
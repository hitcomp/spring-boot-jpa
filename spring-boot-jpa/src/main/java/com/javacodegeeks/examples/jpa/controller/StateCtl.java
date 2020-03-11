package com.javacodegeeks.examples.jpa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacodegeeks.examples.jpa.model.State;
import com.javacodegeeks.examples.jpa.service.StateServiceInt;

@RestController
@RequestMapping("/state")
public class StateCtl extends BaseCtl<State, StateServiceInt<State>> {

}

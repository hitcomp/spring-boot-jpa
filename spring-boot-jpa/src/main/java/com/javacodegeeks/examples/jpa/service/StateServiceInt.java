package com.javacodegeeks.examples.jpa.service;

import com.javacodegeeks.examples.jpa.model.State;
import com.javacodegeeks.examples.jpa.model.UsersContext;

public interface StateServiceInt<T extends State> extends BaseServiceInt<T> {

	public State save(State dto, UsersContext userContext);

}

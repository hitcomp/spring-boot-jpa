package com.javacodegeeks.examples.jpa.service;

import java.util.List;

import com.javacodegeeks.examples.jpa.model.Users;
import com.javacodegeeks.examples.jpa.model.UsersContext;

public interface UserServicelnt<T extends Users> extends BaseServiceInt<T> {

	//public List<T> search(Users object, UsersContext userContext);

	public T save(T dto, UsersContext userContext);
}

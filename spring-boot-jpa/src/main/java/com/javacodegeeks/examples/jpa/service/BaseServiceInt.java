package com.javacodegeeks.examples.jpa.service;

import java.util.List;

import com.javacodegeeks.examples.jpa.model.BaseModel;
import com.javacodegeeks.examples.jpa.model.UsersContext;

public interface BaseServiceInt<T extends BaseModel> {

	public List<T> search(T object, UsersContext userContext);

	public void delete(long id, UsersContext userContext);

	public T save(T dto, UsersContext userContext);

	public T findByPk(long id, UsersContext userContext);

	public boolean Status(long id, boolean staus, String type);
	
	public  int recordCount();
	
	public  List<T> findAll();
}

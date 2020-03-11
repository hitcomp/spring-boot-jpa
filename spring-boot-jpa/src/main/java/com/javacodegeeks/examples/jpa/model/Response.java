package com.javacodegeeks.examples.jpa.model;

import java.util.List;

import org.springframework.http.HttpStatus;

public class Response<T extends BaseModel> {

	private HttpStatus message = null;

	private boolean status = false;

	private T object = null;
	
	private int recordCount = 0;

	
	private List<T> list = null;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public HttpStatus getMessage() {
		return message;
	}

	public void setMessage(HttpStatus message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

}

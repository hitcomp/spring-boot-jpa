package com.javacodegeeks.examples.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "state")
public class State extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "state")
	private String state;

	@Column(name = "description")
	private String description;

	@Column(name = "countryId")
	private long countryId;

	@Column(name = "country")
	private String country;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}

package com.javacodegeeks.examples.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "country")
public class Country extends BaseModel {

	@Column(name = "countryName")
	private String countryName;

	@Column(name = "description")
	private String description;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

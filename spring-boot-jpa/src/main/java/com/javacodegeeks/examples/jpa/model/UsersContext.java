package com.javacodegeeks.examples.jpa.model;

import java.io.Serializable;

public class UsersContext extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userId = 0L;
	private String email = "root";
	private String firstName = null;
	private Users userDTO = null;

	public UsersContext() {
	}

	public UsersContext(Users dto) {
		this.userDTO = dto;
		this.email = dto.getEmail();
		this.firstName = dto.getFirstName();
		this.userId = dto.getId();

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Users getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(Users userDTO) {
		this.userDTO = userDTO;
	}

}

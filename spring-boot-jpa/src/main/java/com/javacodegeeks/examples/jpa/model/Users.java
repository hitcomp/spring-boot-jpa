package com.javacodegeeks.examples.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "users")
public class Users extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "firstName", columnDefinition = "varchar(255) default 'TODO'")
	private String firstName;

	@Column(name = "lastName", columnDefinition = "varchar(255) default 'DODO'")
	private String lastName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password", length = 8)
	private String password;

	@Column(name = "contactNo", length = 10)
	private String contactNo;

	@Column(name = "address")
	private String address;

	@Column(name = "imageId")
	private String imageId;

	@Column(name = "contentType")
	private String contentType;

	@Transient
	private String confirmpassword;

	@Transient
	private String newpassword;

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
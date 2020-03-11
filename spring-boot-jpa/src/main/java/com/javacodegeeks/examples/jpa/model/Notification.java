package com.javacodegeeks.examples.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "notification")
public class Notification extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "code", unique = true, length = 5)
	private String code;

	@Column(name = "subject")
	private String subject;

	@Column(name = "body", length = 3000)
	private String body;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}

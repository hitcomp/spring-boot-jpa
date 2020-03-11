package com.javacodegeeks.examples.jpa.service;

import com.javacodegeeks.examples.jpa.model.Users;

public interface MailServiceInt {

	public void sendEmail(String code, Users user);

	public void sendEmailWithAttachment(String code, Users user);

}

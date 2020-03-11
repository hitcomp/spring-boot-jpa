package com.javacodegeeks.examples.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javacodegeeks.examples.jpa.model.Notification;
import com.javacodegeeks.examples.jpa.model.Response;
import com.javacodegeeks.examples.jpa.service.NotificationServiceInt;

@RestController
@RequestMapping("/Notification")
public class NotificationCtl extends BaseCtl<Notification, NotificationServiceInt<Notification>> {

	@Autowired
	private NotificationServiceInt<Notification> service;

	Response<Notification> response = null;

	@GetMapping("/findByCode/{code}")
	public Response findBYCode(@PathVariable(name = "code") String code) {

		responsese = new Response<Notification>();
		Notification dto = service.findBYCode(code);

		if (dto != null) {
			responsese.setMessage(HttpStatus.FOUND);
			responsese.setObject(dto);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_FOUND);
			responsese.setStatus(false);
		}

		return responsese;
	}

}
package com.javacodegeeks.examples.jpa.service;

import com.javacodegeeks.examples.jpa.model.Notification;

public interface NotificationServiceInt<T extends Notification> extends BaseServiceInt<T> {

	public T findBYCode(String code);

}
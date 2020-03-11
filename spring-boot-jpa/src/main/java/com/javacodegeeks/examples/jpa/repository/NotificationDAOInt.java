package com.javacodegeeks.examples.jpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;

import com.javacodegeeks.examples.jpa.model.Notification;

public interface NotificationDAOInt extends BaseDAOInt<Notification> {
	
	@Transactional
	@Query(nativeQuery = true, name="findBYCode", value ="select * from notification where addeddate =  DATE_SUB(CURDATE(), INTERVAL ?1 DAY)")
	public List findBYCode(String code);
	
	

}
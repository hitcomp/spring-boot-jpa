package com.javacodegeeks.examples.jpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javacodegeeks.examples.jpa.model.Notification;
import com.javacodegeeks.examples.jpa.repository.BaseDAOInt;
import com.javacodegeeks.examples.jpa.repository.NotificationDAOInt;

@Service
public class NotificationServiceImpl<T extends Notification, D extends BaseDAOInt<Notification>>
		extends BaseServiceImpl<Notification> implements NotificationServiceInt<Notification> {

	private static final Logger LOGGER = LogManager.getLogger(NotificationServiceImpl.class);

	@Autowired(required = true)
	private EntityManager entityManager;
	
	@Autowired
	private NotificationDAOInt dao;

	Notification notification = null;

	@Override
	public Notification findBYCode(String code) {
//		if (code != null) {
//			try {
//
//				notification = new Notification();
//
//				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//				CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
//				Root<Notification> rootNotification = criteriaQuery.from(Notification.class);
//				List<Predicate> predicates = new ArrayList<>();
//
//				notification.setCode(code);
//				predicates.add(criteriaBuilder.equal(rootNotification.get("code"), notification.getCode()));
//				criteriaQuery.where(predicates.toArray(new Predicate[0]));
//
//				return entityManager.createQuery(criteriaQuery).getResultList().get(0);
//			} catch (Exception e) {
//				LOGGER.error(e.getMessage());
//			}
//		}
		
		
		System.out.println(code);
		
		List list = dao.findBYCode(code);
		
		System.out.println("list is thiss -----===>");
		
		System.out.println(list.size());
		
		return null;
	}

}
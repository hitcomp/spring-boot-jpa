package com.javacodegeeks.examples.jpa.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.javacodegeeks.examples.jpa.model.Users;
import com.javacodegeeks.examples.jpa.model.UsersContext;
import com.javacodegeeks.examples.jpa.repository.BaseDAOInt;
import com.javacodegeeks.examples.jpa.repository.UserDAOInt;

@Service
public class UserServiceImpl<T extends Users, D extends BaseDAOInt<Users>> extends BaseServiceImpl<Users>
		implements UserServicelnt<Users> {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserDAOInt userDAOInt;

	@Autowired
	private MailServiceInt mailServiceI;

	@Transactional
	@Override
	public Users save(Users dto, UsersContext userContext) {
		if (dto != null) {
			try {
				
				Date date = new Date();
				System.out.println(date);
				if (dto.getId() > 0) {	
					System.out.println(date);
					dto.setUpdateDate(date);
				} else {
					System.out.println(date);
					dto.setAddedDate(date);
					dto.setUpdateDate(date);
				}
				Users baseDto = userDAOInt.save(dto);
				mailServiceI.sendEmailWithAttachment("101", dto);
				return baseDto;
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

		}
		return null;
	}
	


}

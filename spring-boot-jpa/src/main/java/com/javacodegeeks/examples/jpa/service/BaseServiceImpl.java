package com.javacodegeeks.examples.jpa.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacodegeeks.examples.jpa.exceptions.UserNotFoundException;
import com.javacodegeeks.examples.jpa.model.BaseModel;
import com.javacodegeeks.examples.jpa.model.Users;
import com.javacodegeeks.examples.jpa.model.UsersContext;
import com.javacodegeeks.examples.jpa.repository.BaseDAOInt;

@Service
public abstract class BaseServiceImpl<T extends BaseModel> implements BaseServiceInt<T> {

	private static final Logger LOGGER = LogManager.getLogger(BaseServiceImpl.class);

	@Autowired(required = true)
	private BaseDAOInt<T> baseRepo;

	@Autowired
	private EntityManager entityManager;

	@Transactional
	@Override
	public List<T> search(T object, UsersContext userContext) {
		
		try {
				
				Field[] fields = object.getClass().getDeclaredFields();
				
		    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(object.getClass());
				Root rootUser = criteriaQuery.from(object.getClass());
				List<Predicate> predicates = new ArrayList<>();
				int j=0;
				
			    for (int i = 0;i < fields.length; i++) {
			    	 fields[i].setAccessible(true); 
			    	 String name = fields[i].getName();
			    	 Object value = fields[i].get(object);
			    	 System.out.println(name+"  "+value);
			    	 if(value != null && name != "serialVersionUID"){
			    		 System.out.println(value);
			    	 predicates.add(criteriaBuilder.equal(rootUser.get(name),value));
					 criteriaQuery.where(predicates.toArray(new Predicate[j++]));
			    	 }
			    	 }
			    	
			    	int offset=object.getPageNo()*object.getPageSize();
			    	
			    	
			    	System.out.println("off======>"+offset);
			    	
			    	
			    	
			    return (List<T>) entityManager.createQuery(criteriaQuery).setFirstResult(offset)
						.setMaxResults(object.getPageSize()).getResultList();
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
		return null;
		
}
	
	@Transactional
	@Override
	public List<T> findAll() {
		try {
			
			return baseRepo.findAll();
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	

	@Transactional
	@Override
	public void delete(long id, UsersContext userContext) {
		try {
			if (id > 0) {
				baseRepo.deleteById(id);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Transactional
	@Override
	public T save(T dto, UsersContext userContext) {
		try {
			Date date = new Date();
			if (dto != null) {

				if (dto.getId() > 0) {
					dto.setUpdateDate(date);
				} else {
					dto.setAddedDate(date);
					dto.setUpdateDate(date);
				}

				T baseDto = baseRepo.save(dto);
				return baseDto;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Transactional
	@Override
	public T findByPk(long id, UsersContext userContext) {

		try {
			T baseDTO = null;
			try {

				baseDTO = baseRepo.findById(id).orElseThrow(UserNotFoundException::new);
				if (baseDTO != null) {
					return baseDTO;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// changes
	@Transactional
	@Override
	public boolean Status(long id, boolean status, String type) {
		try {
			if (type != null && id > 0) {
				type = type.toLowerCase();
				if (status) {

					entityManager.createQuery("update " + type + " set status = 'active' where id=" + id)
							.executeUpdate();
					return true;
				} else {
					entityManager.createQuery("update " + type + " set status = 'deactive' where id=" + id)
							.executeUpdate();
					return true;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return false;
	}
	
	@Transactional
	@Override
	public  int recordCount(){
		try {
			return (int) baseRepo.count();
			}
		 catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
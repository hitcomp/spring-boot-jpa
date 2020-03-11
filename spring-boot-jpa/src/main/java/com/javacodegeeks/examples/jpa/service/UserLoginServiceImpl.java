package com.javacodegeeks.examples.jpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.javacodegeeks.examples.jpa.model.Users;
import com.javacodegeeks.examples.jpa.model.UsersContext;
import com.javacodegeeks.examples.jpa.repository.UserDAOInt;

@Service
public class UserLoginServiceImpl implements UserLoginServiceInt {

	private static final Logger LOGGER = LogManager.getLogger(UserLoginServiceImpl.class);

	@Autowired
	private UserDAOInt repository;

	@Autowired(required = true)
	private EntityManager entityManager;

	@Autowired
	private HttpSession session;

	@Autowired
	private MailServiceInt mailServiceI;

	@Autowired
	private FileStorageServiceImpl fileStorageService;

	@Autowired
	HttpServletRequest request;

	private static UsersContext userContext = null;

	private Users user = null;

	@Override
	public Users login(String email, String password) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
			Root<Users> rootUser = criteriaQuery.from(Users.class);
			List<Predicate> predicates = new ArrayList<>();

			if (email != null && password != null) {

				predicates.add(criteriaBuilder.equal(rootUser.get("email"), email));
				criteriaQuery.where(predicates.toArray(new Predicate[0]));
				predicates.add(criteriaBuilder.equal(rootUser.get("password"), password));
				criteriaQuery.where(predicates.toArray(new Predicate[1]));
				user = new Users();
				user = (Users) entityManager.createQuery(criteriaQuery).getResultList().get(0);
				if (user != null) {
					if (userContext == null) {
						userContext = new UsersContext(user);
						session.setAttribute("sessionUser", userContext);
						System.out.println("Session id is =====" + session.getId());
						session.setAttribute("sessionId", session.getId());
						return user;
					} else {
						return user;
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Transactional
	@Override
	public Users forgetPass(String newPassword, String confirmNewPassword, String email) {

		try {
			user = new Users();
			if (userContext == null) {

				CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
				CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
				Root<Users> rootUser = criteriaQuery.from(Users.class);
				List<Predicate> predicates = new ArrayList<>();

				if (email != null && newPassword != null && confirmNewPassword != null) {
					predicates.add(criteriaBuilder.equal(rootUser.get("email"), email));
					criteriaQuery.where(predicates.toArray(new Predicate[0]));
					user = entityManager.createQuery(criteriaQuery).getResultList().get(0);
					if (user != null) {
						if (confirmNewPassword.equals(newPassword)) {
							user.setPassword(newPassword);
							repository.save(user);
							// Mail Functionality
							mailServiceI.sendEmail("102", user);
							return user;
						}
					}
				}
			}
		} catch (MailException e) {
			LOGGER.error(e.getMessage());

		}

		return null;
	}

	@Transactional
	@Override
	public Users changePass(String newPassword, String confirmNewPassword) {
		user = new Users();
		try {

			if (userContext != null) {
				if (newPassword != null && confirmNewPassword != null && newPassword.equals(confirmNewPassword)) {
					user = userContext.getUserDTO();
					user.setPassword(newPassword);
					repository.save(user);
					// Mail Functionality
					mailServiceI.sendEmail("103", user);
					session.invalidate();
					userContext = null;
					return user;
				}
			}
		} catch (MailException e) {
			LOGGER.error(e.getMessage());

		}

		return null;
	}

	@Transactional
	@Override
	public boolean deactiveUser() {
		try {
			user = new Users();

			if (userContext != null) {
				user = userContext.getUserDTO();
				user.setStatus("Deactive");
				repository.save(user);
				session.invalidate();
				userContext = null;
				return true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	@Transactional
	@Override
	public Users logout() {
		try {
			user = new Users();

			if (userContext != null) {
				user = userContext.getUserDTO();
				userContext = null;
				session.invalidate();
				return user;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Transactional
	@Override
	public boolean uploadFile(MultipartFile file) {
		try {
			if (userContext != null && file != null) {

				String fileName = fileStorageService.storeFile(file, userContext.getUserId());

				user = userContext.getUserDTO();
				user.setImageId(fileName);
				user.setContentType(file.getContentType());
				userContext = new UsersContext(repository.save(user));
				return true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	@Transactional
	@Override
	public ResponseEntity<Resource> downloadFile() {
		try {
			if (userContext != null) {
				String contentType = null;
				String fileName = null;
				fileName = userContext.getUserDTO().getImageId();
				contentType = userContext.getUserDTO().getContentType();

				if (fileName != null && fileName.length() > 0) {
					Resource resource = fileStorageService.loadFileAsResource(fileName);
					if (resource != null) {
						if (contentType.equals(null)) {
							contentType = "application/octet-stream";
						}
						return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
								.header(HttpHeaders.CONTENT_DISPOSITION,
										"attachment; filename=\"" + resource.getFilename() + "\"")
								.body(resource);

					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	@Override
	public ResponseEntity<Resource> showFile() {
		try {
			if (userContext != null) {
				String contentType = null;
				String fileName = null;
				fileName = userContext.getUserDTO().getImageId();
				contentType = userContext.getUserDTO().getContentType();

				if (fileName != null && fileName.length() > 0) {
					Resource resource = fileStorageService.loadFileAsResource(fileName);
					if (resource != null) {
						if (contentType.equals(null)) {
							contentType = "application/octet-stream";
						}
						return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
								.header(HttpHeaders.CONTENT_DISPOSITION,
										"inline; filename=\"" + resource.getInputStream() + "\"")
								.body(resource);

					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}

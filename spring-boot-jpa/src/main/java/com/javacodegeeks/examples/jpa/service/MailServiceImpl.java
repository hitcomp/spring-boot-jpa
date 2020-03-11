package com.javacodegeeks.examples.jpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.javacodegeeks.examples.jpa.model.Notification;
import com.javacodegeeks.examples.jpa.model.Users;

@Service
public class MailServiceImpl implements MailServiceInt {

	private static final Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);

	@Autowired(required = true)
	private JavaMailSender javaMailSender;

	@Autowired
	private EntityManager entityManager;

	public void sendEmail(String code, Users user) {
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
			Root<Notification> rootNotification = criteriaQuery.from(Notification.class);
			List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(rootNotification.get("code"), code));
			criteriaQuery.where(predicates.toArray(new Predicate[0]));

			Notification notification = (Notification) entityManager.createQuery(criteriaQuery).getResultList().get(0);
			if (notification != null) {

				// String mailBody = MailService.mailBody(user, code);

				String body = notification.getBody();
				String userName = user.getFirstName() + " " + user.getLastName() + ",";
				body = body.replace("userName", userName);
				body = body.replace("userEmail", user.getEmail());
				body = body.replace("userPassword", user.getPassword());
				body = body.replace("br", "\n");
				body = body.replace("tb", "\t");

				simpleMailMessage.setTo(user.getEmail());
				simpleMailMessage.setSubject(notification.getSubject());
				simpleMailMessage.setText("JAI HO"

				);
			}

			new Thread(new Runnable() {
				public void run() {
					javaMailSender.send(simpleMailMessage);
					System.out.println("mail send Successfully");
				}
			}).start();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		}
	}

	public void sendEmailWithAttachment(String code, Users user) {

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			// mimeMessage.setContent("text/html");

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
			Root<Notification> rootNotification = criteriaQuery.from(Notification.class);
			List<Predicate> predicates = new ArrayList<>();

			predicates.add(criteriaBuilder.equal(rootNotification.get("code"), code));
			criteriaQuery.where(predicates.toArray(new Predicate[0]));

			Notification notification = (Notification) entityManager.createQuery(criteriaQuery).getResultList().get(0);
			if (notification != null) {

				// String mailBody = MailService.mailBody(user, code);

				// String name = user.getImageId()"";
				String body = notification.getBody();
				String userName = user.getFirstName() + " " + user.getLastName() + ",";
				body = body.replace("USERNAME", userName);
				body = body.replace("USEREMAIL", user.getEmail());
				body = body.replace("USERPASSWORD", user.getPassword());
				// body = body.replace("USERPASSWORD",name);

//			body = body.replace("br","\n");
//			body = body.replace("tb","\t");

				mimeMessageHelper.setTo(user.getEmail());
				mimeMessageHelper.setSubject(notification.getSubject());

				// FileSystemResource res = new FileSystemResource(new
				// File("/home/lavishpatel/Documents/workspace-spring-tool-suite-4-4.5.0.RELEASE/spring-boot-jpa/uploadfiles/age.jpg"));

				// ClassPathResource classPathResource = new
				// ClassPathResource("/home/lavishpatel/Documents/logo.png");
				FileSystemResource file = new FileSystemResource("/home/lavishpatel/Downloads/age.jpg");
				mimeMessageHelper.addInline("imagemime", file);
				mimeMessageHelper.setText("<html><body><img src=imagemime></body></html>", true);
//		
//		   System.out.println(file);
				mimeMessageHelper.addAttachment(file.getFilename(), file);
			}

			new Thread(new Runnable() {
				public void run() {
					javaMailSender.send(mimeMessage);
					System.out.println("mail send Successfully");
				}
			}).start();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}

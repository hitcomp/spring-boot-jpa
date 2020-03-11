package com.javacodegeeks.examples.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javacodegeeks.examples.jpa.model.Response;
import com.javacodegeeks.examples.jpa.model.Users;
import com.javacodegeeks.examples.jpa.service.FileStorageServiceImpl;
import com.javacodegeeks.examples.jpa.service.UserLoginServiceInt;
import com.javacodegeeks.examples.jpa.service.UserServicelnt;

@RestController
@RequestMapping("User")
public class Userctl extends BaseCtl<Users, UserServicelnt<Users>> {

	@Autowired
	private UserLoginServiceInt loginservice;

	@Autowired
	private FileStorageServiceImpl fileStorageService;

	Response<Users> response = null;

	@PostMapping("/login")
	public Response Login(@RequestParam(name = "email") String email, 
			@RequestParam(name = "password") String password) {
		System.out.println(email);
		System.out.println(password);

		Users user = loginservice.login(email, password);
		responsese = new Response<Users>();

		if (user != null) {
			responsese.setMessage(HttpStatus.ACCEPTED);
			responsese.setObject(user);
			responsese.setStatus(true);	
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
			responsese.setStatus(false);
		}

		return responsese;
	}

	// Method For Forget Password

	@PostMapping("/forgetPassword")
	public Response forgetPassword(@RequestParam(name = "newPassword") String newPassword,
			@RequestParam(name = "confirmNewPassword") String confirmNewPassword,
			@RequestParam(name = "email") String email) throws Exception {

		Users user = loginservice.forgetPass(newPassword, confirmNewPassword, email);
		responsese = new Response<Users>();

		if (user != null) {

			responsese.setMessage(HttpStatus.ACCEPTED);
			responsese.setObject(user);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
			responsese.setStatus(false);
		}
		return responsese;

	}

	// Method For Change Password

	@PostMapping("/changePassword")
	public Response changePassword(@RequestParam(name = "newPassword") String newPassword,
			@RequestParam(name = "confirmNewPassword") String confirmNewPassword) throws Exception {

		Users user = loginservice.changePass(newPassword, confirmNewPassword);
		responsese = new Response<Users>();

		if (user != null) {

			responsese.setMessage(HttpStatus.ACCEPTED);
			responsese.setObject(user);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
			responsese.setStatus(false);
		}
		return responsese;
	}

	// Method For Deactive User

	@DeleteMapping(value = "/deactive")
	public Response deactiveUser() {

		responsese = new Response<Users>();
		if (loginservice.deactiveUser()) {
			responsese.setMessage(HttpStatus.ACCEPTED);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
		}
		return responsese;
	}

	// Method For logout

	@GetMapping("/logout")
	public Response logout() {

		Users user = loginservice.logout();
		responsese = new Response<Users>();
		if (user != null) {
			responsese.setMessage(HttpStatus.ACCEPTED);
			responsese.setObject(user);
			responsese.setStatus(true);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
			responsese.setStatus(false);
		}
		return responsese;

	}

	@PostMapping("/uploadimage")
	public Response uploadIMG(@RequestParam("file") MultipartFile file) {

		responsese = new Response<Users>();
		if (loginservice.uploadFile(file)) {
			responsese.setMessage(HttpStatus.ACCEPTED);
		} else {
			responsese.setMessage(HttpStatus.NOT_ACCEPTABLE);
		}
		return responsese;
	}

	@GetMapping("/downloadimage")
	public ResponseEntity<Resource> downloadIMG() {

		responsese = new Response<Users>();
		return loginservice.downloadFile();

	}

	@GetMapping("/showimage")
	public ResponseEntity<Resource> showIMG() {

		responsese = new Response<Users>();
		return loginservice.showFile();

	}
}
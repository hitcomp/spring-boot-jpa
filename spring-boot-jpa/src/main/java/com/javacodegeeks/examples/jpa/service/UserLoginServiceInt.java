package com.javacodegeeks.examples.jpa.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.javacodegeeks.examples.jpa.model.Users;

public interface UserLoginServiceInt {

	public Users login(String email, String password);

	public Users forgetPass(String newPassword, String confirmNewPassword, String email);

	public Users changePass(String newPassword, String confirmNewPassword);

	public boolean deactiveUser();

	public Users logout();

	public boolean uploadFile(MultipartFile file);

	public ResponseEntity<Resource> downloadFile();

	public ResponseEntity<Resource> showFile();
}

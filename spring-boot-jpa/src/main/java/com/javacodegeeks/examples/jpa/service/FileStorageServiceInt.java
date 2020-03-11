package com.javacodegeeks.examples.jpa.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageServiceInt {

	public String storeFile(MultipartFile file, long id);

	public Resource loadFileAsResource(String fileName);

}

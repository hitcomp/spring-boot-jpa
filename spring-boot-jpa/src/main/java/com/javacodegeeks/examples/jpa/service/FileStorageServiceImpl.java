package com.javacodegeeks.examples.jpa.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javacodegeeks.examples.jpa.Config.FileStorageProperties;
import com.javacodegeeks.examples.jpa.exceptions.FileStorageException;

@Service
public class FileStorageServiceImpl implements FileStorageServiceInt {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir());

		// .toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			LOGGER.error("Could not create the directory where the uploaded files will be stored.", ex);
		}
	}

	@Override
	public String storeFile(MultipartFile file, long id) {
		// Normalize file name

		long nano_startTime = System.nanoTime();
		String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		currentDate = currentDate + "" + nano_startTime;

		String fileName = file.getOriginalFilename().replace(file.getOriginalFilename(), currentDate.toLowerCase());

		// String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				LOGGER.error(new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName));
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			LOGGER.error("Could not store file " + fileName + ". Please try again!", ex);
		}
		return null;
	}

	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName);

			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				LOGGER.error("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			LOGGER.error("File not found " + fileName, ex);
		}
		return null;
	}

}
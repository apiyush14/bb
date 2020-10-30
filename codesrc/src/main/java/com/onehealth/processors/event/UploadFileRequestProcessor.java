package com.onehealth.processors.event;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onehealth.config.FileStorageConfig;
import com.onehealth.core.processor.RequestProcessor;

@Component
public class UploadFileRequestProcessor extends RequestProcessor<MultipartFile, Boolean> {

	@Autowired
	FileStorageConfig fileStorageProperties;

	@Override
	public Boolean doProcessing(MultipartFile request) throws Exception {
		String fileName = StringUtils.cleanPath(request.getOriginalFilename());
		Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()+fileName).toAbsolutePath().normalize();
		try {
			Files.copy(request.getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

}

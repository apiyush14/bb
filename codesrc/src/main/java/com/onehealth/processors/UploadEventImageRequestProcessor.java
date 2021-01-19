package com.onehealth.processors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onehealth.config.FileStorageConfig;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.UploadEventImageRequest;

@Component
public class UploadEventImageRequestProcessor extends RequestProcessor<UploadEventImageRequest, Boolean> {

	@Autowired
	FileStorageConfig fileStorageProperties;

	@Override
	public Boolean doProcessing(UploadEventImageRequest request) throws Exception {
		String fileName = StringUtils.cleanPath(request.getEventId() + "_" + request.getImageType() + "." + request
				.getFile().getOriginalFilename().substring(request.getFile().getOriginalFilename().indexOf('.') + 1));
		Path fileStorageDir = Paths.get(fileStorageProperties.getUploadDir() + request.getEventId() + "/");
		Files.createDirectories(fileStorageDir);
		Path fileStorageLocation = Paths
				.get(fileStorageProperties.getUploadDir() + request.getEventId() + "/" + fileName).toAbsolutePath()
				.normalize();
		try {
			Files.copy(request.getFile().getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

}

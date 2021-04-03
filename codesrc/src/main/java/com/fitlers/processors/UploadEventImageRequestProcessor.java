package com.fitlers.processors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fitlers.config.FileStorageConfig;
import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.model.request.UploadEventImageRequest;

@Component
public class UploadEventImageRequestProcessor extends RequestProcessor<UploadEventImageRequest, Boolean> {

	@Autowired
	FileStorageConfig fileStorageProperties;

	public static final Logger logger = LoggerFactory.getLogger(UploadEventImageRequestProcessor.class);

	@Override
	public Boolean doProcessing(UploadEventImageRequest request) throws Exception {
		logger.info("UploadEventImageRequestProcessor doProcessing Started for event id " + request.getEventId());
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
			logger.error("UploadEventImageRequestProcessor doProcessing Failed for event id " + request.getEventId());
			throw e;
		}
		logger.info("UploadEventImageRequestProcessor doProcessing Completed for event id " + request.getEventId());
		return true;
	}

}

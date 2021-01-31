package com.onehealth.processors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onehealth.config.FileStorageConfig;
import com.onehealth.core.exceptions.MediaFileStorageException;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.UploadEventImageRequest;

@Component
public class UploadEventImageRequestProcessor extends RequestProcessor<UploadEventImageRequest, Boolean> {

	@Autowired
	FileStorageConfig fileStorageProperties;
	
	private static final Logger LOG = Logger.getLogger(UploadEventImageRequestProcessor.class);

	@Override
	public Boolean doProcessing(UploadEventImageRequest request) {
		String fileName = StringUtils.cleanPath(request.getEventId() + "_" + request.getImageType() + "." + request
				.getFile().getOriginalFilename().substring(request.getFile().getOriginalFilename().indexOf('.') + 1));
		Path fileStorageDir = Paths.get(fileStorageProperties.getUploadDir() + request.getEventId() + "/");
		LOG.debug("Uploading Event Display Image.");
		try {
		Files.createDirectories(fileStorageDir);
		Path fileStorageLocation = Paths
				.get(fileStorageProperties.getUploadDir() + request.getEventId() + "/" + fileName).toAbsolutePath()
				.normalize();
		Files.copy(request.getFile().getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException e) {
		 LOG.error("Unable to upload event image file", e.getCause());
		 throw new MediaFileStorageException(e);
		}
		LOG.debug("Event Display Image uploaded.");
		return true;
	}

}

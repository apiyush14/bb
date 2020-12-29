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
import com.onehealth.entities.EventDetails;
import com.onehealth.model.request.UploadEventImageRequest;
import com.onehealth.repo.EventDetailsRepository;

@Component
public class UploadEventImageRequestProcessor extends RequestProcessor<UploadEventImageRequest, Boolean> {

	@Autowired
	FileStorageConfig fileStorageProperties;
	@Autowired
	EventDetailsRepository eventDetailsRepository;

	@Override
	public Boolean doProcessing(UploadEventImageRequest request) throws Exception {
		String fileName = StringUtils.cleanPath(request.getFile().getOriginalFilename());
		Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()+fileName).toAbsolutePath().normalize();
		try {
			Files.copy(request.getFile().getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
			EventDetails eventDetails=eventDetailsRepository.getOne(request.getEventId());
			eventDetails.setEventDisplayPic(fileStorageLocation.toString());
			eventDetailsRepository.save(eventDetails);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

}

package com.fitlers.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.fitlers.config.FileStorageConfig;
import com.fitlers.core.processor.RequestProcessor;
import com.fitlers.model.request.GetDisplayImageForEventRequest;

@Component
public class GetDisplayImageForEventRequestProcessor extends RequestProcessor<GetDisplayImageForEventRequest, byte[]> {

	@Autowired
	FileStorageConfig fileStorageProperties;
	
	public static final Logger logger = LoggerFactory.getLogger(GetDisplayImageForEventRequestProcessor.class);

	@Override
	public byte[] doProcessing(GetDisplayImageForEventRequest request) throws Exception {
		logger.info("GetDisplayImageForEventRequestProcessor doProcessing Started for Event Id " + request.getEventId());
		InputStream in = null;
		File eventDirectory = new File(fileStorageProperties.getUploadDir() + request.getEventId() + "/");
		String[] eventImageFiles = eventDirectory.list();
		for (String eventImageFile : eventImageFiles) {
			if (eventImageFile.contains(request.getImageType())) {
				in = new FileInputStream(eventDirectory.getAbsolutePath() + "/" + eventImageFile);
				break;
			}
		}
		logger.info("GetDisplayImageForEventRequestProcessor doProcessing Completed for Event Id " + request.getEventId());
		return StreamUtils.copyToByteArray(in);
	}

}

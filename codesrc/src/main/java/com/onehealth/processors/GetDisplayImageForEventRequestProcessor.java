package com.onehealth.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.onehealth.config.FileStorageConfig;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.GetDisplayImageForEventRequest;

@Component
public class GetDisplayImageForEventRequestProcessor extends RequestProcessor<GetDisplayImageForEventRequest, byte[]> {

	@Autowired
	FileStorageConfig fileStorageProperties;

	@Override
	public byte[] doProcessing(GetDisplayImageForEventRequest request) throws Exception {
		InputStream in = null;
		File eventDirectory = new File(fileStorageProperties.getUploadDir() + request.getEventId() + "/");
		String[] eventImageFiles = eventDirectory.list();
		for (String eventImageFile : eventImageFiles) {
			if (eventImageFile.contains(request.getImageType())) {
				in = new FileInputStream(eventDirectory.getAbsolutePath() + "/" + eventImageFile);
				break;
			}
		}
		return StreamUtils.copyToByteArray(in);
	}

}

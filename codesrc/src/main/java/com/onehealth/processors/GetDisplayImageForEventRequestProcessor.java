package com.onehealth.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.onehealth.config.FileStorageConfig;
import com.onehealth.core.exceptions.MediaFileStorageException;
import com.onehealth.core.processor.RequestProcessor;
import com.onehealth.model.request.GetDisplayImageForEventRequest;

@Component
public class GetDisplayImageForEventRequestProcessor extends RequestProcessor<GetDisplayImageForEventRequest, byte[]> {

	@Autowired
	FileStorageConfig fileStorageProperties;

	private static final Logger LOG = Logger.getLogger(GetDisplayImageForEventRequestProcessor.class);

	@Override
	public byte[] doProcessing(GetDisplayImageForEventRequest request) {
		InputStream in = null;
		File eventDirectory = new File(fileStorageProperties.getUploadDir() + request.getEventId() + "/");
		String[] eventImageFiles = eventDirectory.list();
		LOG.debug("Retreving Event Display Image.");
		try {
		for (String eventImageFile : eventImageFiles) {
			if (eventImageFile.contains(request.getImageType())) {
				in = new FileInputStream(eventDirectory.getAbsolutePath() + "/" + eventImageFile);
				break;
			}
		}
		 byte[] imgeData = StreamUtils.copyToByteArray(in);
		 LOG.debug("Retrieved Event Display Image.");
		 return imgeData;
		}
		catch(FileNotFoundException ex) {
			LOG.error("Unable to find event image file", ex.getCause());
			throw new MediaFileStorageException();
		}
		catch(IOException ex) {
			LOG.error("Event file found, error occured during retrieving file data", ex.getCause());
			throw new MediaFileStorageException();
		}
		finally {
			if(Objects.nonNull(in))
				try {
					in.close();
				} catch (IOException e) {
					LOG.error("Error occured during closing file resources", e.getCause());
				}		 	
		}
		
	}

}

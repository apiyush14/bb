package com.fitlers.core.encryption;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Encrypter {

	public static final Logger logger = LoggerFactory.getLogger(Encrypter.class);

	public String encrypt(String key, String value) {
		try {
			SecretKeySpec secretKey = EncryptionUtils.getKey(key);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes("UTF-8")));
		} catch (Exception e) {
			logger.error("Encrypter encrypt failed, returning plain text");
		}
		return value;
	}
}

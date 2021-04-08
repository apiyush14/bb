package com.fitlers.core.encryption;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Decrypter {

	public static final Logger logger = LoggerFactory.getLogger(Decrypter.class);

	public String decrypt(String key, String value) {
		try {
			SecretKeySpec secretKey = EncryptionUtils.getKey(key);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
		} catch (Exception e) {
			logger.error("Decrypter decrypt failed, returning encrypted value");
		}
		return value;
	}
}

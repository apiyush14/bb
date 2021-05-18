package com.fitlers.core.encryption;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.fitlers.core.annotations.Encrypted;

public class EncryptionUtils {

	public static final Logger logger = LoggerFactory.getLogger(EncryptionUtils.class);

	public static boolean isFieldEncrypted(Field field) {
		return AnnotationUtils.findAnnotation(field, Encrypted.class) != null;
	}

	public static int getPropertyIndex(String name, String[] properties) {
		for (int i = 0; i < properties.length; i++) {
			if (name.equals(properties[i])) {
				return i;
			}
		}
		throw new IllegalArgumentException("No property was found for name " + name);
	}

	public static SecretKeySpec getKey(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key;
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			return new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}

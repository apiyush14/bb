package com.fitlers.core.encryption;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fitlers.core.annotations.Encrypted;

@Component
public class FieldDecrypter {
	@Autowired
	private Decrypter decrypter;

	public void decrypt(Object[] state, String[] propertyNames, Object entity) {
		ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, state, propertyNames),
				EncryptionUtils::isFieldEncrypted);
	}

	private void decryptField(Field field, Object[] state, String[] propertyNames) {
		Encrypted encryptedField = field.getAnnotation(Encrypted.class);
		int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
		Object currentValue = state[propertyIndex];
		if (currentValue != null) {
			if (currentValue instanceof Collection) {
				throw new IllegalStateException("Encrypted annotation was used on a non-String field");
			}
			state[propertyIndex] = decrypter.decrypt(encryptedField.encryptionKey(), currentValue.toString());
		}
	}
}

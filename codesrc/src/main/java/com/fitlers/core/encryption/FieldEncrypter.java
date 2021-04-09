package com.fitlers.core.encryption;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fitlers.core.annotations.Encrypted;

@Component
public class FieldEncrypter {

	@Autowired
	private Encrypter encrypter;

	public void encrypt(Object[] state, String[] propertyNames, Object entity) {
		ReflectionUtils.doWithFields(entity.getClass(), field -> encryptField(field, state, propertyNames),
				EncryptionUtils::isFieldEncrypted);
	}

	private void encryptField(Field field, Object[] state, String[] propertyNames) {
		Encrypted encryptedField = field.getAnnotation(Encrypted.class);
		int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
		Object currentValue = state[propertyIndex];
		if (currentValue != null) {
			if (currentValue instanceof Collection) {
				throw new IllegalStateException("Encrypted annotation was used on a non-String field");
			}
			state[propertyIndex] = encrypter.encrypt(encryptedField.encryptionKey(), currentValue.toString());
		}
	}

}

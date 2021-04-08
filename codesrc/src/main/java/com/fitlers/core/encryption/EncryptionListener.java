package com.fitlers.core.encryption;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreLoadEvent;
import org.hibernate.event.spi.PreLoadEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncryptionListener implements PreInsertEventListener, PreUpdateEventListener, PreLoadEventListener {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FieldEncrypter fieldEncrypter;
	@Autowired
	private FieldDecrypter fieldDecrypter;

	@Override
	public void onPreLoad(PreLoadEvent event) {
		Object[] state = event.getState();
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object entity = event.getEntity();
		fieldDecrypter.decrypt(state, propertyNames, entity);
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		Object[] state = event.getState();
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object entity = event.getEntity();
		fieldEncrypter.encrypt(state, propertyNames, entity);
		return false;
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		Object[] state = event.getState();
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object entity = event.getEntity();
		fieldEncrypter.encrypt(state, propertyNames, entity);
		return false;
	}

}

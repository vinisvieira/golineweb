package br.com.goline2.model.dao.impl;

import javax.persistence.EntityManager;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Notification;

public class NotificationDAO extends GenericDAO<Long, Notification> {

	public NotificationDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}

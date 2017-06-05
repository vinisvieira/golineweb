package br.com.goline2.model.jpa.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.goline2.model.jpa.IJPAUtil;

public class JPAUtil implements IJPAUtil {

	private EntityManager entityManager;
	private EntityManagerFactory factory;
	
	private static JPAUtil instance;

	public static synchronized JPAUtil getInstance(String persistenceUnitName) {
		
		if (instance == null)
			instance = new JPAUtil();
		
		instance.factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		instance.entityManager = instance.factory.createEntityManager();
		return instance;
	}
	
	private JPAUtil() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void beginTransaction(){
		this.entityManager.getTransaction().begin();
	}

	@Override
	public void commit(){
		this.entityManager.getTransaction().commit();
	}

	/**
	 * THIS METHOD NEEDS TO BE ALWAYS CALLED
	 */
	@Override
	public void close(){
		this.entityManager.clear();
		this.entityManager.close();
		this.factory.close();
		System.gc();
	}

	@Override
	public void rollBack(){
		this.entityManager.getTransaction().rollback();
	}

	@Override
	public EntityManager getEntityManager(){
		return this.entityManager;
	}
	
	public void clear(){
		this.entityManager.clear();
	}

}

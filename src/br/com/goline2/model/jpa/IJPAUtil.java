package br.com.goline2.model.jpa;

import javax.persistence.EntityManager;

public interface IJPAUtil {
	
	public void beginTransaction();
	public void commit();
	public void close();
	public void rollBack();
	public EntityManager getEntityManager();

}

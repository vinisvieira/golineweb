package br.com.goline2.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Administrador;

public class AdministradorDAO extends GenericDAO<Long, Administrador> {

	public AdministradorDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public boolean emailExiste(String email) {
		EntityManager entityManager = super.getEntityManager();

		Query query = entityManager.createQuery("SELECT u FROM Administrador u WHERE u.email = :email");
		query.setParameter("email", email);
		return (query.getResultList().size() > 0);
	}

}

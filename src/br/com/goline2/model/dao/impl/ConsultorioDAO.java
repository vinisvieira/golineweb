package br.com.goline2.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Consultorio;

public class ConsultorioDAO extends GenericDAO<Long, Consultorio> {

	public ConsultorioDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public boolean findByName(String name) {

		EntityManager entityManager = super.getEntityManager();

		Query query = entityManager.createQuery("SELECT u FROM Consultorio u WHERE u.nome = :name");
		query.setParameter("name", name);
		return (query.getResultList().size() > 0);

	}

}

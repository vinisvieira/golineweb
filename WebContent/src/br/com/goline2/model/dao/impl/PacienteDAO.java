package br.com.goline2.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Paciente;

public class PacienteDAO extends GenericDAO<Long, Paciente> {

	public PacienteDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public boolean validarEmail(String email) {
		EntityManager entityManager = super.getEntityManager();

		Query query = entityManager.createQuery("SELECT u FROM Paciente u WHERE u.email = :email");
		query.setParameter("email", email);
		return (query.getResultList().size() > 0);
	}

}

package br.com.goline2.model.dao.impl;

import javax.persistence.EntityManager;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Consultorio;

public class ConsultorioDAO extends GenericDAO<Long, Consultorio> {

	public ConsultorioDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

}

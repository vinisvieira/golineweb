package br.com.goline2.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Senha;
import br.com.goline2.util.Constants;
import br.com.goline2.util.MyDateGenerator;

public class SenhaDAO extends GenericDAO<Long, Senha> {

	public SenhaDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public List<Long> pegarUltimaSenha(Long id) {
		EntityManager entityManager = super.getEntityManager();

		TypedQuery<Long> query = entityManager
				.createQuery("SELECT MAX(id) FROM Senha WHERE consultorio_id = :id AND dataInicio = :data", Long.class);

		query.setParameter("id", id);
		query.setParameter("data", MyDateGenerator.getCurrentDate());

		return query.getResultList();
	}

	public List<Long> pegarUltimaSenhaChamada(Long id) {
		EntityManager entityManager = super.getEntityManager();

		TypedQuery<Long> query = entityManager
				.createQuery("SELECT MAX(id) FROM Senha WHERE consultorio_id = :id AND dataInicio = :data"
						+ " AND statusChamada = :status", Long.class);

		query.setParameter("id", id);
		query.setParameter("data", MyDateGenerator.getCurrentDate());
		query.setParameter("status", Constants.ACTIVE_ENTITY);

		return query.getResultList();
	}
	
	public List<Long> pegarQuantidadeSenha(Long id) {
		EntityManager entityManager = super.getEntityManager();

		TypedQuery<Long> query = entityManager
				.createQuery("SELECT COUNT(id) FROM Senha WHERE consultorio_id = :idConsultorio AND dataInicio = :data", Long.class);

		query.setParameter("idConsultorio", id);
		query.setParameter("data", MyDateGenerator.getCurrentDate());

		return query.getResultList();
	}

	public List<Senha> pegarSenhasDia(Long id) {

		EntityManager entityManager = super.getEntityManager();

		TypedQuery<Senha> query = entityManager.createQuery("SELECT u FROM Senha u WHERE u.consultorio.id = :id "
				+ "AND u.dataInicio = :data AND u.statusChamada = :status", Senha.class);

		query.setParameter("id", id);
		query.setParameter("data", MyDateGenerator.getCurrentDate());
		query.setParameter("status", Constants.INACTIVE_ENTITY);

		return query.getResultList();

	}

	public List<Senha> pegarSenhasChamadas(Long id) {
		EntityManager entityManager = super.getEntityManager();

		TypedQuery<Senha> query = entityManager.createQuery("SELECT u FROM Senha u WHERE u.consultorio.id = :id "
				+ "AND u.dataInicio = :data AND u.statusChamada = :status", Senha.class);

		query.setParameter("id", id);
		query.setParameter("data", MyDateGenerator.getCurrentDate());
		query.setParameter("status", Constants.ACTIVE_ENTITY);

		return query.getResultList();

	}

}

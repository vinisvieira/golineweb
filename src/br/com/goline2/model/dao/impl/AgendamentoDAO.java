package br.com.goline2.model.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.Agendamento;
import br.com.goline2.util.Constants;
import br.com.goline2.util.MyDateGenerator;

public class AgendamentoDAO extends GenericDAO<Long, Agendamento> {

	public AgendamentoDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public boolean validarData(Date data, Date hora, Long idConsultorio) {
		EntityManager entityManager = super.getEntityManager();

		Query query = entityManager.createQuery("SELECT u FROM Agendamento u WHERE u.dataAgendamento = :data "
				+ "AND u.horaAgendamento = :hora AND u.consultorio.id = :id AND u.status = :status");
		query.setParameter("data", data);
		query.setParameter("hora", hora);
		query.setParameter("id", idConsultorio);
		query.setParameter("status", Constants.ACTIVE_ENTITY);
		return (query.getResultList().size() > 0);
	}

	public List<Agendamento> agendamentoByConsultorio(Long idConsultorio) {
		EntityManager entityManager = super.getEntityManager();

		TypedQuery<Agendamento> query = entityManager.createQuery(
				"SELECT u FROM Agendamento u WHERE u.consultorio.id = :id AND u.dataAgendamento = :data",
				Agendamento.class);
		query.setParameter("id", idConsultorio);
		query.setParameter("data", MyDateGenerator.getCurrentDate());

		return query.getResultList();

	}

}

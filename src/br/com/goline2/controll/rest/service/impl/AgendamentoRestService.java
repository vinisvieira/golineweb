package br.com.goline2.controll.rest.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.com.goline2.model.dao.impl.AgendamentoDAO;
import br.com.goline2.model.entity.impl.Agendamento;
import br.com.goline2.model.jpa.impl.JPAUtil;
import br.com.goline2.util.Constants;
import br.com.goline2.util.JSONUtil;
import br.com.goline2.util.ResponseBuilderGenerator;

@Path("/agendamento")
public class AgendamentoRestService {

	@Context
	private HttpServletRequest servletRequest;

	private AgendamentoDAO agendamentoDAO;
	private JPAUtil simpleEntityManager;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response create() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.agendamentoDAO = new AgendamentoDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Agendamento agendamento = new Gson().fromJson(servletRequest.getReader(), Agendamento.class);

			if (!this.agendamentoDAO.validarData(agendamento.getDataAgendamento(),
					agendamento.getHoraAgendamento(), agendamento.getConsultorio().getId())) {

				this.agendamentoDAO.save(agendamento);
				this.simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

			} else {

				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);

			}

		} catch (Exception e) {
			e.printStackTrace();
			this.simpleEntityManager.rollBack();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();

	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{idAgendamento}")
	@PermitAll
	public Response delete(@PathParam("idAgendamento") Long idAgendamento) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.agendamentoDAO = new AgendamentoDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Agendamento agendamento = this.agendamentoDAO.getById(idAgendamento);

			agendamento.setStatus(Constants.INACTIVE_ENTITY);
			this.simpleEntityManager.commit();

			responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

		} catch (Exception e) {
			e.printStackTrace();
			this.simpleEntityManager.rollBack();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}
		return responseBuilder.build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@Path("/{idConsultorio}")
	public Response getByConsultorio(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.agendamentoDAO = new AgendamentoDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Agendamento> agendamentos = this.agendamentoDAO.agendamentoByConsultorio(idConsultorio);
			List<Agendamento> agendamentoJson = new ArrayList<>();

			for (Agendamento agendamento : agendamentos) {

				if (agendamento.isStatus() == Constants.ACTIVE_ENTITY) {

					agendamento.setConsultorio(null);
					agendamento.setPaciente(null);
					agendamentoJson.add(agendamento);
				}

			}

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(agendamentoJson));

		} catch (Exception e) {
			e.printStackTrace();
			this.simpleEntityManager.rollBack();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}
		return responseBuilder.build();

	}

}

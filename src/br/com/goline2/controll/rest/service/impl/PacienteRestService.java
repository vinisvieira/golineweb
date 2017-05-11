package br.com.goline2.controll.rest.service.impl;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.com.goline2.model.dao.impl.ConsultorioDAO;
import br.com.goline2.model.dao.impl.PacienteDAO;
import br.com.goline2.model.entity.impl.Consultorio;
import br.com.goline2.model.entity.impl.Paciente;
import br.com.goline2.model.jpa.impl.JPAUtil;
import br.com.goline2.util.Constants;
import br.com.goline2.util.JSONUtil;
import br.com.goline2.util.ResponseBuilderGenerator;
import br.com.goline2.util.StringUtil;

@Path("/paciente")
public class PacienteRestService {

	@Context
	private HttpServletRequest servletRequest;

	private JPAUtil simpleEntityManager;
	private PacienteDAO pacienteDAO;
	private ConsultorioDAO consultorioDAO;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response create() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.pacienteDAO = new PacienteDAO(simpleEntityManager.getEntityManager());
		Paciente paciente = new Paciente();

		try {
			this.simpleEntityManager.beginTransaction();

			paciente = new Gson().fromJson(servletRequest.getReader(), Paciente.class);

			if (!this.pacienteDAO.validarEmail(paciente.getEmail())) {

				paciente.setPassword(StringUtil.SHA1(paciente.getPassword()));
				paciente.setStatus(Constants.ACTIVE_ENTITY);
				pacienteDAO.save(paciente);
				simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			} else {

				System.out.println("Email Invalido");
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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/cadastro-rapido/{idConsultorio}")
	@PermitAll
	public Response createRapido(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.pacienteDAO = new PacienteDAO(simpleEntityManager.getEntityManager());
		this.consultorioDAO = new ConsultorioDAO(simpleEntityManager.getEntityManager());
		ArrayList<Consultorio> consultorios = new ArrayList<>();
		Consultorio consultorio = new Consultorio();
		Paciente paciente = new Paciente();

		try {
			this.simpleEntityManager.beginTransaction();

			paciente = new Gson().fromJson(servletRequest.getReader(), Paciente.class);
			consultorio = this.consultorioDAO.getById(idConsultorio);

			if (!this.pacienteDAO.validarEmail(paciente.getEmail())) {

				consultorios.add(consultorio);

				paciente.setPassword(StringUtil.SHA1(paciente.getTelefone()));
				paciente.setEmail(paciente.getNome());
				paciente.setConsultorio(consultorios);
				paciente.setStatus(Constants.ACTIVE_ENTITY);

				pacienteDAO.save(paciente);
				simpleEntityManager.commit();

				if (paciente != null) {

					SenhaRestService restService = new SenhaRestService();

					Response.accepted(restService.create(idConsultorio, paciente.getId()));

				}

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain
						(Response.ok());
			} else {

				System.out.println("Email Invalido");
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.simpleEntityManager.rollBack();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			
			if (simpleEntityManager.getEntityManager().isOpen()) {
				
				this.simpleEntityManager.close();
			
			}
		}
			
		return responseBuilder.build();

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response update() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.pacienteDAO = new PacienteDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Paciente paciente = new Gson().fromJson(servletRequest.getReader(), Paciente.class);

			pacienteDAO.update(paciente);
			simpleEntityManager.commit();

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

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{idPaciente}")
	@PermitAll
	public Response delete(@PathParam("idPaciente") Long idPaciente) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.pacienteDAO = new PacienteDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Paciente paciente = this.pacienteDAO.getById(idPaciente);

			paciente.setStatus(Constants.INACTIVE_ENTITY);

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
	@Path("/{idPaciente}")
	@PermitAll
	public Response getById(@PathParam("idPaciente") Long idPaciente) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.pacienteDAO = new PacienteDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Paciente paciente = this.pacienteDAO.getById(idPaciente);

			paciente.setAgendamento(null);
			paciente.setConsultorio(null);
			paciente.setSenha(null);
			paciente.setPassword(null);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(paciente));

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

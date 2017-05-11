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
import br.com.goline2.model.entity.impl.Consultorio;
import br.com.goline2.model.jpa.impl.JPAUtil;
import br.com.goline2.util.Constants;
import br.com.goline2.util.JSONUtil;
import br.com.goline2.util.ResponseBuilderGenerator;

@Path("/consultorio")
public class ConsultorioRestService {

	@Context
	private HttpServletRequest servletRequest;

	private ConsultorioDAO consultorioDAO;
	private JPAUtil simpleEntityManager;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response create() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.consultorioDAO = new ConsultorioDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Consultorio consultorio = new Gson().fromJson(servletRequest.getReader(), Consultorio.class);

			if (!consultorioDAO.findByName(consultorio.getNome())) {

				this.consultorioDAO.save(consultorio);
				this.simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			} else {
				System.out.println("Consultorio ja cadastrado!!");
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response update() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.consultorioDAO = new ConsultorioDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Consultorio consultorio = new Gson().fromJson(servletRequest.getReader(), Consultorio.class);

			consultorio.setStatus(Constants.ACTIVE_ENTITY);

			this.consultorioDAO.update(consultorio);
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

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{idConsultorio}")
	@PermitAll
	public Response delete(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.consultorioDAO = new ConsultorioDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Consultorio consultorio = this.consultorioDAO.getById(idConsultorio);

			consultorio.setStatus(Constants.INACTIVE_ENTITY);

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
	public Response read() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.consultorioDAO = new ConsultorioDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Consultorio> consultorios = consultorioDAO.findAll();
			List<Consultorio> consultoriosJSON = new ArrayList<>();

			for (Consultorio consultorio : consultorios) {
				if (consultorio.getStatus() != false) {
					consultorio.setAdministradores(null);
					consultorio.setAgendamento(null);
					consultorio.setPacientes(null);
					consultorio.setSenhas(null);
					consultoriosJSON.add(consultorio);
				}
			}

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(consultoriosJSON));

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
	@Path("/{idConsultorio}")
	@PermitAll
	public Response getById(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.consultorioDAO = new ConsultorioDAO(simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Consultorio consultorio = this.consultorioDAO.getById(idConsultorio);

			consultorio.setAdministradores(null);
			consultorio.setAgendamento(null);
			consultorio.setPacientes(null);
			consultorio.setSenhas(null);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(consultorio));

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

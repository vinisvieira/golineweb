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

import br.com.goline2.model.dao.impl.AdministradorDAO;
import br.com.goline2.model.entity.impl.Administrador;
import br.com.goline2.model.entity.impl.Consultorio;
import br.com.goline2.model.entity.impl.Paciente;
import br.com.goline2.model.jpa.impl.JPAUtil;
import br.com.goline2.util.Constants;
import br.com.goline2.util.JSONUtil;
import br.com.goline2.util.ResponseBuilderGenerator;
import br.com.goline2.util.StringUtil;

@Path("/administrador")
public class AdministradorRestService {

	@Context
	private HttpServletRequest servletRequest;

	private JPAUtil simpleEntityManager;
	private AdministradorDAO administradorDAO;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Response create() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.administradorDAO = new AdministradorDAO(simpleEntityManager.getEntityManager());
		Administrador administrador = new Administrador();

		try {
			this.simpleEntityManager.beginTransaction();

			administrador = new Gson().fromJson(servletRequest.getReader(), Administrador.class);

			if (!administradorDAO.emailExiste(administrador.getEmail())) {

				administrador.setPassword(StringUtil.SHA1(administrador.getPassword()));

				administradorDAO.save(administrador);
				simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

			} else {

				System.out.println("EMAIL JA CADASTRADO");
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
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Administrador administrador = new Gson().fromJson(servletRequest.getReader(), Administrador.class);

			if (!administrador.getPassword().equals(null)) {
				administrador.setPassword(StringUtil.SHA1(administrador.getPassword()));
			}

			this.administradorDAO.update(administrador);
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
	@Path("/{idAdministrador}")
	@PermitAll
	public Response delete(@PathParam("idAdministrador") Long idAdministrador) throws IOException {
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Administrador administrador = this.administradorDAO.getById(idAdministrador);

			administrador.setStatus(Constants.INACTIVE_USER);

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
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Administrador> administradores = administradorDAO.findAll();
			List<Administrador> administradoresJson = new ArrayList<>();

			for (Administrador administrador : administradores) {
				if (administrador.isStatus() != false) {
					administrador.setConsultorio(null);
					administrador.setPassword(null);
					administradoresJson.add(administrador);
				}
			}
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(administradoresJson));

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
	@Path("/{idAdm}")
	@PermitAll
	public Response getById(@PathParam("idAdm") Long idAdm) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Administrador administrador = administradorDAO.getById(idAdm);

			administrador.setConsultorio(null);
			administrador.setPassword(null);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(administrador));

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
	@Path("/pacientes/{idAdm}")
	@PermitAll
	public Response getListPaciente(@PathParam("idAdm") Long idAdm) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		Administrador administrador = new Administrador();
		List<Consultorio> consultorios = new ArrayList<Consultorio>();
		List<Paciente> pacientes = new ArrayList<Paciente>();
		Consultorio consultorio = new Consultorio();
		int index = 0;

		try {
			this.simpleEntityManager.beginTransaction();

			administrador = this.administradorDAO.getById(idAdm);

			if (administrador != null) {
				for (int i = 0; i < administrador.getConsultorio().size(); i++) {
					consultorio = administrador.getConsultorio().get(i);
					consultorio.setAdministradores(null);
					consultorio.setAgendamento(null);
					consultorio.setSenhas(null);
					consultorios.add(consultorio);
				}
			} else {
				System.out.println("adm nulo");
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}
			for (Paciente paciente : consultorios.get(index).getPacientes()) {

				paciente.setAgendamento(null);
				paciente.setConsultorio(null);
				paciente.setSenha(null);
				pacientes.add(paciente);
				index++;
			}

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(pacientes));

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

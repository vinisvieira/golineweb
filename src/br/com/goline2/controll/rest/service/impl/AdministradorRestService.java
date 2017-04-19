package br.com.goline2.controll.rest.service.impl;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.com.goline2.model.dao.impl.AdministradorDAO;
import br.com.goline2.model.entity.impl.Administrador;
import br.com.goline2.model.jpa.impl.JPAUtil;
import br.com.goline2.util.Constants;
import br.com.goline2.util.ResponseBuilderGenerator;
import br.com.goline2.util.StringUtil;

@Path("/administradores")
public class AdministradorRestService {

	@Context
	private HttpServletRequest servletRequest;

	private JPAUtil simpleEntityManager;
	private AdministradorDAO administradorDAO;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed("ADMINISTRADOR")
	private Response create() throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Administrador administrador = new Gson().fromJson(servletRequest.getReader(), Administrador.class);

			if (!administradorDAO.loginExiste(administrador.getLogin())) {

				administrador.setPassword(StringUtil.SHA1(administrador.getPassword()));
				administrador.setStatus(Constants.ACTIVE_USER);

				this.administradorDAO.save(administrador);
				this.simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

			} else {

				System.out.println("LOGIN JA EXISTE");
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
	@RolesAllowed("ADMINISTRADOR")
	public Response update() throws IOException {
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.administradorDAO = new AdministradorDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			Administrador administrador = new Gson().fromJson(servletRequest.getReader(), Administrador.class);

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

}

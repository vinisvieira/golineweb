package br.com.goline2.controll.rest.service.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.com.goline2.model.dao.impl.ConsultorioDAO;
import br.com.goline2.model.dao.impl.PacienteDAO;
import br.com.goline2.model.dao.impl.SenhaDAO;
import br.com.goline2.model.entity.impl.Consultorio;
import br.com.goline2.model.entity.impl.Paciente;
import br.com.goline2.model.entity.impl.Senha;
import br.com.goline2.model.jpa.impl.JPAUtil;
import br.com.goline2.util.Constants;
import br.com.goline2.util.JSONUtil;
import br.com.goline2.util.MyDateGenerator;
import br.com.goline2.util.ResponseBuilderGenerator;

@Path("/senha")
public class SenhaRestService {

	@Context
	private HttpServletRequest servletRequest;

	private JPAUtil simpleEntityManager;
	private SenhaDAO senhaDAO;
	private ConsultorioDAO consultorioDAO;
	private PacienteDAO pacienteDAO;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("criar/{idConsultorio}/{idPaciente}")
	@PermitAll
	public Response create(@PathParam("idConsultorio") Long idConsultorio, @PathParam("idPaciente") Long idPaciente)
			throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.senhaDAO = new SenhaDAO(this.simpleEntityManager.getEntityManager());
		this.consultorioDAO = new ConsultorioDAO(this.simpleEntityManager.getEntityManager());
		this.pacienteDAO = new PacienteDAO(this.simpleEntityManager.getEntityManager());

		Consultorio consultorio = new Consultorio();
		Senha senha = new Senha();
		Senha ultimaSenha = new Senha();

		try {
			this.simpleEntityManager.beginTransaction();

			List<Long> idSenha = this.senhaDAO.pegarUltimaSenha(idConsultorio);
			consultorio = this.consultorioDAO.getById(idConsultorio);

			String datahoje = MyDateGenerator.dateToString(MyDateGenerator.getCurrentDate());

			if (idPaciente != 0) {

				Paciente paciente = this.pacienteDAO.getById(idPaciente);

				senha.setPaciente(paciente);

			}

			if (idSenha.get(0) == null) {

				senha.setValorChamada(1);
				senha.setDataInicio(MyDateGenerator.dateStringToSql(datahoje));
				senha.setStatus(Constants.ACTIVE_USER);
				senha.setConsultorio(consultorio);

				senhaDAO.save(senha);
				simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

			} else {

				ultimaSenha = this.senhaDAO.getById(idSenha.get(0));
				String dataBanco = MyDateGenerator.dateToString(ultimaSenha.getDataInicio());

				if (datahoje.equals(dataBanco)) {

					senha.setValorChamada(ultimaSenha.getValorChamada() + 1);
					senha.setDataInicio(MyDateGenerator.dateStringToSql(datahoje));
					senha.setStatus(Constants.ACTIVE_USER);
					senha.setConsultorio(consultorio);

					senhaDAO.save(senha);
					simpleEntityManager.commit();

					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

				} else {

					senha.setValorChamada(1);
					senha.setDataInicio(MyDateGenerator.dateStringToSql(datahoje));
					senha.setStatus(Constants.ACTIVE_USER);
					senha.setConsultorio(consultorio);

					senhaDAO.save(senha);
					simpleEntityManager.commit();

					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

				}
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("android/{idConsultorio}")
	@PermitAll
	public Response getById(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.senhaDAO = new SenhaDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Senha> senhas = this.senhaDAO.pegarSenhasDia(idConsultorio);

			senhas.get(0).setConsultorio(null);
			senhas.get(0).setDataInicio(null);
			senhas.get(0).setDataFinal(null);
			senhas.get(0).setPaciente(null);

			Senha senha = new Senha();

			senha = senhas.get(0);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(senha));

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
	public Response getSenhaById(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.senhaDAO = new SenhaDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Senha> senhas = this.senhaDAO.pegarSenhasDia(idConsultorio);

			senhas.get(0).setStatusChamada(Constants.ACTIVE_ENTITY);
			senhas.get(0).setDataFinal(MyDateGenerator.getCurrentDate());

			this.senhaDAO.update(senhas.get(0));
			this.simpleEntityManager.commit();

			senhas.get(0).setConsultorio(null);
			senhas.get(0).setPaciente(null);
			senhas.get(0).setDataInicio(null);
			senhas.get(0).setDataFinal(null);

			Senha senha = new Senha();

			senha = senhas.get(0);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(senha));

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
	@Path("/next/{idConsultorio}")
	@PermitAll
	public Response getNext(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.senhaDAO = new SenhaDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Long> idSenha = this.senhaDAO.pegarUltimaSenhaChamada(idConsultorio);

			Senha senha = this.senhaDAO.getById(idSenha.get(0));

			senha.setValorChamada(senha.getValorChamada() + 1);
			senha.setConsultorio(null);
			senha.setPaciente(null);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(senha));

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
	@Path("/quant/{idConsultorio}")
	@PermitAll
	public Response getQuantidade(@PathParam("idConsultorio") Long idConsultorio) throws IOException {

		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = JPAUtil.getInstance(Constants.PERSISTENCE_UNIT_NAME);
		this.senhaDAO = new SenhaDAO(this.simpleEntityManager.getEntityManager());

		try {
			this.simpleEntityManager.beginTransaction();

			List<Long> quant = this.senhaDAO.pegarQuantidadeSenha(idConsultorio);

			int quantidade = Integer.valueOf((quant.get(0).toString()));

			quantidade = quantidade + 1;

			String quantSenha = String.valueOf(quantidade);

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, quantSenha);

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

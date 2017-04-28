package br.com.goline2.controll.rest.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.goline2.controll.rest.service.impl.AdministradorRestService;
import br.com.goline2.controll.rest.service.impl.AgendamentoRestService;
import br.com.goline2.controll.rest.service.impl.ConsultorioRestService;
import br.com.goline2.controll.rest.service.impl.PacienteRestService;
import br.com.goline2.controll.rest.service.impl.SenhaRestService;

public class MyRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public MyRestApplication() {

		singletons.add(new AdministradorRestService());
		singletons.add(new AgendamentoRestService());
		singletons.add(new ConsultorioRestService());
		singletons.add(new PacienteRestService());
		singletons.add(new SenhaRestService());

	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
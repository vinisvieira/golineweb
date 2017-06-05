package br.com.goline2.controll.rest.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.goline2.controll.rest.service.impl.AdministradorRestService;
import br.com.goline2.controll.rest.service.impl.AgendamentoRestService;
import br.com.goline2.controll.rest.service.impl.ConsultorioRestService;
import br.com.goline2.controll.rest.service.impl.NotificationRestService;
import br.com.goline2.controll.rest.service.impl.PacienteRestService;
import br.com.goline2.controll.rest.service.impl.SenhaRestService;

public class MyRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public MyRestApplication() {

		singletons.addAll(Arrays.asList(new AdministradorRestService(), 
										new AgendamentoRestService(),
										new ConsultorioRestService(), 
										new PacienteRestService(), 
										new SenhaRestService(),
										new NotificationRestService()));

	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
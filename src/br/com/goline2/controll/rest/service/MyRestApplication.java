package br.com.goline2.controll.rest.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MyRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public MyRestApplication() {

	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
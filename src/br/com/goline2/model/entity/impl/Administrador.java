package br.com.goline2.model.entity.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Administrador extends User {

	private List<Consultorio> consultorio;

	@ManyToMany(mappedBy = "administradores")
	public List<Consultorio> getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(List<Consultorio> consultorio) {
		this.consultorio = consultorio;
	}

	@Override
	public String toString() {
		return "Administrador [Id()=" + getId() + ", Nome()=" + getNome()
				+ ", Login()=" + getLogin() + ", Password()=" + getPassword() + "]";
	}

}

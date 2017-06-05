package br.com.goline2.model.entity.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Paciente extends User {

	private String telefone;
	private List<Consultorio> consultorio;
	private List<Agendamento> agendamento;
	private List<Senha> senha;

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@ManyToMany(mappedBy = "pacientes")
	public List<Consultorio> getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(List<Consultorio> consultorio) {
		this.consultorio = consultorio;
	}

	@OneToMany(mappedBy = "paciente", targetEntity = Agendamento.class)
	public List<Agendamento> getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(List<Agendamento> agendamento) {
		this.agendamento = agendamento;
	}

	@OneToMany(mappedBy = "paciente", targetEntity = Senha.class)
	public List<Senha> getSenha() {
		return senha;
	}

	public void setSenha(List<Senha> senha) {
		this.senha = senha;
	}

}

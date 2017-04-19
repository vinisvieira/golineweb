package br.com.goline2.model.entity.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Consultorio {

	private Long id;
	private String nome;
	private String rua;
	private String bairro;
	private String numero;
	private String cep;
	private String especialidade;
	private boolean status;
	private boolean statusFuncionamento;
	private List<Administrador> administradores;
	private List<Paciente> pacientes;
	private List<Senha> senhas;
	private List<Agendamento> agendamento;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatusFuncionamento() {
		return statusFuncionamento;
	}

	public void setStatusFuncionamento(boolean statusFuncionamento) {
		this.statusFuncionamento = statusFuncionamento;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public List<Administrador> getAdministradores() {
		return administradores;
	}

	public void setAdministradores(List<Administrador> administradores) {
		this.administradores = administradores;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	@OneToMany(mappedBy = "consultorio", targetEntity = Senha.class)
	public List<Senha> getSenhas() {
		return senhas;
	}

	public void setSenhas(List<Senha> senhas) {
		this.senhas = senhas;
	}

	@OneToMany(mappedBy = "consultorio", targetEntity = Agendamento.class)
	public List<Agendamento> getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(List<Agendamento> agendamento) {
		this.agendamento = agendamento;
	}

	@Override
	public String toString() {
		return "Consultorio [id=" + id + ", nome=" + nome + ", rua=" + rua + ", bairro=" + bairro + ", numero=" + numero
				+ ", cep=" + cep + ", status=" + status + ", statusFuncionamento=" + statusFuncionamento
				+ ", administradores=" + administradores + ", pacientes=" + pacientes + ", senhas=" + senhas + "]";
	}

}

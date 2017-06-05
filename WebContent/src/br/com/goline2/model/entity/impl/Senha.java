package br.com.goline2.model.entity.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Senha {

	private Long id;
	private int valorChamada;
	private boolean status;
	private boolean statusChamada;
	private Date dataInicio;
	private Date dataFinal;
	private Consultorio consultorio;
	private Paciente paciente;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getValorChamada() {
		return valorChamada;
	}

	public void setValorChamada(int valorChamada) {
		this.valorChamada = valorChamada;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatusChamada() {
		return statusChamada;
	}

	public void setStatusChamada(boolean statusChamada) {
		this.statusChamada = statusChamada;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Temporal(TemporalType.DATE)
	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	@ManyToOne
	@JoinColumn(name = "consultorio_id")
	public Consultorio getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(Consultorio consultorio) {
		this.consultorio = consultorio;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "paciente_id")
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}

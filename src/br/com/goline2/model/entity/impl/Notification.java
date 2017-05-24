package br.com.goline2.model.entity.impl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Notification {

	private Long id;
	private String tokenFMC;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Lob
	public String getTokenFMC() {
		return tokenFMC;
	}

	public void setTokenFMC(String tokenFMC) {
		this.tokenFMC = tokenFMC;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", tokenFMC=" + tokenFMC + "]";
	}

}

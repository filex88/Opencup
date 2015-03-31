package it.dipe.opencup.dto;

import java.io.Serializable;

public class RicercaLiberaDTO implements Serializable {
	
	private static final long serialVersionUID = 1439602670562686509L;

	private String cercaPerKeyword;

	public String getCercaPerKeyword() {
		return cercaPerKeyword;
	}

	public void setCercaPerKeyword(String cercaPerKeyword) {
		this.cercaPerKeyword = cercaPerKeyword;
	}
	
	

}

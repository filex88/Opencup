package it.dipe.opencup.dto;

import java.io.Serializable;

public class RicercaLiberaDTO implements Serializable {
	
	private static final long serialVersionUID = 1439602670562686509L;

	private String cercaPerKeyword;
	private String tipoRicerca;

	public String getCercaPerKeyword() {
		return cercaPerKeyword;
	}

	public void setCercaPerKeyword(String cercaPerKeyword) {
		this.cercaPerKeyword = cercaPerKeyword;
	}

	public String getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}
	
	

}

package it.dipe.opencup.dto;

import java.io.Serializable;


public class TotaliDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1260439578725692619L;

	private Double impoCostoProgetto;

	private Double impoImportoFinanziato;

	public Double getImpoCostoProgetto() {
		return impoCostoProgetto;
	}

	public void setImpoCostoProgetto(Double impoCostoProgetto) {
		this.impoCostoProgetto = impoCostoProgetto;
	}

	public Double getImpoImportoFinanziato() {
		return impoImportoFinanziato;
	}

	public void setImpoImportoFinanziato(Double impoImportoFinanziato) {
		this.impoImportoFinanziato = impoImportoFinanziato;
	}
	
	
}

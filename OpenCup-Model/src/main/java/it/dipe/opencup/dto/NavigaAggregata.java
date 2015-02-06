package it.dipe.opencup.dto;

import java.io.Serializable;

public class NavigaAggregata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 143973242554717243L;
	
	private String idNatura = "-1";
	private String idSettoreInternvanto = "-1";
	private String idSottosettoreIntervento = "-1";
	private String idCategoriaIntervento = "-1";
	
	private String idAnnoDecisione = "-1";
	
	private String idProvincia = "-1";
	private String idRegione = "-1";
	private String idAreaGeografica = "-1";
	private String descStato = "ITALIA";
	
	public String toString(){
		String toString = "";
		
		toString = toString + "idNatura: (" + idNatura + "); ";
		toString = toString + "idSettoreInternvanto: (" + idSettoreInternvanto + "); ";
		toString = toString + "idSottosettoreIntervento: (" + idSottosettoreIntervento + "); ";
		toString = toString + "idCategoriaIntervento: (" + idCategoriaIntervento + "); ";
		toString = toString + "idAnnoDecisione: (" + idAnnoDecisione + "); ";
		toString = toString + "idProvincia: (" + idProvincia + "); ";
		toString = toString + "idRegione: (" + idRegione + "); ";
		toString = toString + "idAreaGeografica: (" + idAreaGeografica + "); ";
		toString = toString + "descStato: (" + descStato + "); ";
		
		return toString;
	}

	public String getIdNatura() {
		return idNatura;
	}

	public void setIdNatura(String idNatura) {
		this.idNatura = idNatura;
	}

	public String getIdSettoreInternvanto() {
		return idSettoreInternvanto;
	}

	public void setIdSettoreInternvanto(String idSettoreInternvanto) {
		this.idSettoreInternvanto = idSettoreInternvanto;
	}

	public String getIdSottosettoreIntervento() {
		return idSottosettoreIntervento;
	}

	public void setIdSottosettoreIntervento(String idSottosettoreIntervento) {
		this.idSottosettoreIntervento = idSottosettoreIntervento;
	}

	public String getIdCategoriaIntervento() {
		return idCategoriaIntervento;
	}

	public void setIdCategoriaIntervento(String idCategoriaIntervento) {
		this.idCategoriaIntervento = idCategoriaIntervento;
	}

	public String getIdAnnoDecisione() {
		return idAnnoDecisione;
	}

	public void setIdAnnoDecisione(String idAnnoDecisione) {
		this.idAnnoDecisione = idAnnoDecisione;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(String idRegione) {
		this.idRegione = idRegione;
	}

	public String getIdAreaGeografica() {
		return idAreaGeografica;
	}

	public void setIdAreaGeografica(String idAreaGeografica) {
		this.idAreaGeografica = idAreaGeografica;
	}

	public String getDescStato() {
		return descStato;
	}

	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}
	
}

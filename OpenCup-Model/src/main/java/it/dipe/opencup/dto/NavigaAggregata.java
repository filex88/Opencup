package it.dipe.opencup.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private List<String> idAnnoDecisiones = new ArrayList<String>();
	
	private String idRegione = "-1";
	private String idProvincia = "-1";
	private String idComune = "-1";
	
	private String idAreaGeografica = "-1";
	private String descStato = "ITALIA";

	private String idCategoriaSoggetto = "-1";
	private String idSottoCategoriaSoggetto = "-1";
	
	private String idTipologiaInterventi = "-1";
	private String idStatoProgetto = "-1";
	
	public String toString(){
		String toString = "";
		
		toString = toString + "idNatura: (" + idNatura + "); ";
		toString = toString + "idSettoreInternvanto: (" + idSettoreInternvanto + "); ";
		toString = toString + "idSottosettoreIntervento: (" + idSottosettoreIntervento + "); ";
		toString = toString + "idCategoriaIntervento: (" + idCategoriaIntervento + "); ";
		toString = toString + "idAnnoDecisione: (" + idAnnoDecisione + "); ";
		toString = toString + "idRegione: (" + idRegione + "); ";
		toString = toString + "idProvincia: (" + idProvincia + "); ";
		toString = toString + "idComune: (" + idComune + "); ";
		toString = toString + "idAreaGeografica: (" + idAreaGeografica + "); ";
		toString = toString + "descStato: (" + descStato + "); ";
		toString = toString + "idAnnoDecisiones: (" + idAnnoDecisiones + "); ";
		toString = toString + "idCategoriaSoggetto: (" + idCategoriaSoggetto + "); ";
		toString = toString + "idSottoCategoriaSoggetto: (" + idSottoCategoriaSoggetto + "); ";
		toString = toString + "idTipologiaInterventi: (" + idTipologiaInterventi + "); ";
		toString = toString + "idStatoProgetto: (" + idStatoProgetto + "); ";
		
		return toString;
	}

	public boolean isFiltroClassificazione(){
		boolean retval=false;
		if(! "-1".equals( idAnnoDecisione ) ){
			 retval=true;
		}else
		if(idAnnoDecisiones.size()>0){
			 retval=true;
		}else
		if(! "-1".equals( idRegione ) ){
			 retval=true;
		}else
		if(! "-1".equals( idProvincia ) ){
			 retval=true;
		}else
		if(! "-1".equals( idComune ) ){
			 retval=true;
		}else
		if(! "-1".equals( idAreaGeografica ) ){
			 retval=true;
		}else
		if(! "ITALIA".equals( descStato ) ){
			 retval=true;
		}else
		if(! "-1".equals( idCategoriaSoggetto ) ){
			 retval=true;
		}else
		if(! "-1".equals( idSottoCategoriaSoggetto ) ){
			 retval=true;
		}else
		if(! "-1".equals( idTipologiaInterventi ) ){
			 retval=true;
		}else
		if(! "-1".equals( idStatoProgetto ) ){
			 retval=true;
		}
		
		return retval;
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

	public List<String> getIdAnnoDecisiones() {
		return idAnnoDecisiones;
	}

	public void setIdAnnoDecisiones(List<String> idAnnoDecisiones) {
		this.idAnnoDecisiones = idAnnoDecisiones;
	}

	public String getIdRegione() {
		return idRegione;
	}

	public void setIdRegione(String idRegione) {
		this.idRegione = idRegione;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdComune() {
		return idComune;
	}

	public void setIdComune(String idComune) {
		this.idComune = idComune;
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

	public String getIdCategoriaSoggetto() {
		return idCategoriaSoggetto;
	}

	public void setIdCategoriaSoggetto(String idCategoriaSoggetto) {
		this.idCategoriaSoggetto = idCategoriaSoggetto;
	}

	public String getIdSottoCategoriaSoggetto() {
		return idSottoCategoriaSoggetto;
	}

	public void setIdSottoCategoriaSoggetto(String idSottoCategoriaSoggetto) {
		this.idSottoCategoriaSoggetto = idSottoCategoriaSoggetto;
	}

	public String getIdTipologiaInterventi() {
		return idTipologiaInterventi;
	}

	public void setIdTipologiaInterventi(String idTipologiaInterventi) {
		this.idTipologiaInterventi = idTipologiaInterventi;
	}

	public String getIdStatoProgetto() {
		return idStatoProgetto;
	}

	public void setIdStatoProgetto(String idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
	}
	
}

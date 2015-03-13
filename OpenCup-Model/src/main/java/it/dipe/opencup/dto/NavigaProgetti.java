package it.dipe.opencup.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigaProgetti implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8974832497625407563L;
	
	private String idProgetto;
	
	private String naviga;
	
	private String pagAggregata;
	private String pagElencoProgetti;
	private String pagDettaglioProgetto;
	
	private String idNatura;
	private String idAreaIntervento;
	private String idSottosettoreIntervento;
	private String idCategoriaIntervento;
	
	private List<String> idAnnoDecisiones;
	
	private String idAreaGeografica;
	private String descStato;
	
	private String idTipologiaIntervento;
	private String idStatoProgetto;
	
	private String idCategoriaSoggetto;
	private String idSottoCategoriaSoggetto;
	
	private String idRegione;
	private String idProvincia;
	private String idComune;
	
	public void importa(NavigaAggregata navigaAggregata) {
		
		this.pagAggregata = navigaAggregata.getPagAggregata();
		this.pagElencoProgetti = navigaAggregata.getPagElencoProgetti();
		this.pagDettaglioProgetto = navigaAggregata.getPagDettaglioProgetto();
		
		this.naviga = navigaAggregata.getNaviga();
		this.idNatura = navigaAggregata.getIdNatura();
		this.idAreaIntervento = navigaAggregata.getIdAreaIntervento();
		this.idSottosettoreIntervento = navigaAggregata.getIdSottosettoreIntervento();
		this.idCategoriaIntervento = navigaAggregata.getIdCategoriaIntervento();
		
		this.idCategoriaSoggetto = navigaAggregata.getIdCategoriaSoggetto();
		this.idSottoCategoriaSoggetto = navigaAggregata.getIdSottoCategoriaSoggetto();
		this.idTipologiaIntervento = navigaAggregata.getIdTipologiaIntervento();
		this.idStatoProgetto = navigaAggregata.getIdStatoProgetto();
		this.idAreaGeografica = navigaAggregata.getIdAreaGeografica();
		this.descStato = navigaAggregata.getDescStato();
		this.idRegione = navigaAggregata.getIdRegione();
		this.idProvincia = navigaAggregata.getIdProvincia();
		this.idComune = navigaAggregata.getIdComune();

		this.idAnnoDecisiones = new ArrayList<String>();
		this.idAnnoDecisiones.add("-1");
		
		this.idProgetto = "-1";
		
	}
	
	public NavigaProgetti(){
		this.pagAggregata = "";
		this.pagElencoProgetti = "";
		this.pagDettaglioProgetto = "";
		this.naviga = "";
		this.idNatura = "-1";
		this.idAreaIntervento = "-1";
		this.idSottosettoreIntervento = "-1";
		this.idCategoriaIntervento = "-1";
		this.idAnnoDecisiones = new ArrayList<String>();
		this.idAnnoDecisiones.add("-1");
		this.idCategoriaSoggetto = "-1";
		this.idSottoCategoriaSoggetto = "-1";
		this.idTipologiaIntervento = "-1";
		this.idStatoProgetto = "-1";
		this.idAreaGeografica = "-1";
		this.descStato = "ITALIA";
		this.idRegione = "-1";
		this.idProvincia = "-1";
		this.idComune = "-1";
		this.idProgetto = "-1";
	}

	public String toString(){
		String toString = "";
		toString = toString + "idNatura: (" + idNatura + "); ";
		toString = toString + "idAreaIntervento: (" + idAreaIntervento + "); ";
		toString = toString + "idSottosettoreIntervento: (" + idSottosettoreIntervento + "); ";
		toString = toString + "idCategoriaIntervento: (" + idCategoriaIntervento + "); ";
		toString = toString + "idRegione: (" + idRegione + "); ";
		toString = toString + "idProvincia: (" + idProvincia + "); ";
		toString = toString + "idComune: (" + idComune + "); ";
		toString = toString + "idAreaGeografica: (" + idAreaGeografica + "); ";
		toString = toString + "descStato: (" + descStato + "); ";
		toString = toString + "idAnnoDecisiones: (" + idAnnoDecisiones + "); ";
		toString = toString + "idCategoriaSoggetto: (" + idCategoriaSoggetto + "); ";
		toString = toString + "idSottoCategoriaSoggetto: (" + idSottoCategoriaSoggetto + "); ";
		toString = toString + "idTipologiaIntervento: (" + idTipologiaIntervento + "); ";
		toString = toString + "idStatoProgetto: (" + idStatoProgetto + "); ";
		toString = toString + "naviga: (" + naviga + "); ";
		toString = toString + "pagAggregata: (" + pagAggregata + "); ";
		toString = toString + "pagElencoProgetti: (" + pagElencoProgetti + "); ";
		toString = toString + "pagDettaglioProgetto: (" + pagDettaglioProgetto + "); ";
		toString = toString + "idProgetto: (" + idProgetto + "); ";
		return toString;
	}
	
	public String getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(String idProgetto) {
		this.idProgetto = idProgetto;
	}

	public String getNaviga() {
		return naviga;
	}

	public void setNaviga(String naviga) {
		this.naviga = naviga;
	}

	public String getIdNatura() {
		return idNatura;
	}

	public void setIdNatura(String idNatura) {
		this.idNatura = idNatura;
	}

	public String getIdAreaIntervento() {
		return idAreaIntervento;
	}

	public void setIdAreaIntervento(String idAreaIntervento) {
		this.idAreaIntervento = idAreaIntervento;
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

	public List<String> getIdAnnoDecisiones() {
		return idAnnoDecisiones;
	}

	public void setIdAnnoDecisiones(List<String> idAnnoDecisiones) {
		this.idAnnoDecisiones = idAnnoDecisiones;
	}

	public String getIdTipologiaIntervento() {
		return idTipologiaIntervento;
	}

	public void setIdTipologiaIntervento(String idTipologiaIntervento) {
		this.idTipologiaIntervento = idTipologiaIntervento;
	}

	public String getIdStatoProgetto() {
		return idStatoProgetto;
	}

	public void setIdStatoProgetto(String idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
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

	public String getPagAggregata() {
		return pagAggregata;
	}

	public void setPagAggregata(String pagAggregata) {
		this.pagAggregata = pagAggregata;
	}

	public String getPagElencoProgetti() {
		return pagElencoProgetti;
	}

	public void setPagElencoProgetti(String pagElencoProgetti) {
		this.pagElencoProgetti = pagElencoProgetti;
	}

	public String getPagDettaglioProgetto() {
		return pagDettaglioProgetto;
	}

	public void setPagDettaglioProgetto(String pagDettaglioProgetto) {
		this.pagDettaglioProgetto = pagDettaglioProgetto;
	}

}

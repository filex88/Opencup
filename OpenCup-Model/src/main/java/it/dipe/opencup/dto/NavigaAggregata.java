package it.dipe.opencup.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigaAggregata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 143973242554717243L;
	
	public static final String NAVIGA_CLASSIFICAZIONE = "classificazione";
	public static final String NAVIGA_LOCALIZZAZIONE = "localizzazione";
	
	private String pagAggregata;
	private String pagElencoProgetti;
	private String pagDettaglioProgetto;
	
	private String naviga;
	private boolean importaInNavigaProgetti;
	private String distribuzione;
	
	private String idNatura;
	private String idAreaIntervento;
	private String idSottosettoreIntervento;
	private String idCategoriaIntervento;
	
	private List<String> idAnnoAggregatos;
	
	private String idRegione;
	private String idProvincia;
	private String idComune;
	
	private String idAreaGeografica;
	private String descStato;

	private String idCategoriaSoggetto;
	private String idSottoCategoriaSoggetto;
	
	private String idTipologiaIntervento;
	private String idStatoProgetto;
	
	private String descNatura;
	private String descAreaIntervento;
	private String descSottosettoreIntervento;
	private String descCategoriaIntervento;
	
	private String descAnnoAggregatos;
	
	private String descRegione;
	private String descProvincia;
	private String descComune;
	
	private String descAreaGeografica;

	private String descCategoriaSoggetto;
	private String descSottoCategoriaSoggetto;
	
	private String descTipologiaIntervento;
	private String descStatoProgetto;
	
	public NavigaAggregata(String naviga, String id){
		this.naviga = naviga;
		this.distribuzione = "VOLUME";
		
		if(NAVIGA_CLASSIFICAZIONE.equals(this.naviga)){
			this.idNatura = id;
			this.idAreaIntervento = "0";
			this.pagAggregata = "natura";
			this.pagElencoProgetti = "natelencoprogetti";
			this.pagDettaglioProgetto = "natdettaglioprogetto";
		}else{
			this.idNatura = "-1";
			this.idAreaIntervento = "-1";
		}

		this.idSottosettoreIntervento = "-1";
		this.idCategoriaIntervento = "-1";
		this.idAnnoAggregatos = new ArrayList<String>();
		this.idAnnoAggregatos.add("-1");
		this.idRegione = "-1";
		this.idProvincia = "-1";
		this.idComune = "-1";
		this.idAreaGeografica = "-1";
		this.descStato = "ITALIA";
		this.idCategoriaSoggetto = "-1";
		this.idSottoCategoriaSoggetto = "-1";
		this.idTipologiaIntervento = "-1";
		this.idStatoProgetto = "-1";
	}

	public NavigaAggregata(){
		this.distribuzione = "VOLUME";
		this.pagAggregata = "";
		this.pagElencoProgetti = "";
		this.pagDettaglioProgetto = "";
		this.naviga = "";
		this.idNatura = "-1";
		this.idAreaIntervento = "-1";
		this.idSottosettoreIntervento = "-1";
		this.idCategoriaIntervento = "-1";
		this.idAnnoAggregatos = new ArrayList<String>();
		this.idAnnoAggregatos.add("-1");
		this.idRegione = "-1";
		this.idProvincia = "-1";
		this.idComune = "-1";
		this.idAreaGeografica = "-1";
		this.descStato = "ITALIA";
		this.idCategoriaSoggetto = "-1";
		this.idSottoCategoriaSoggetto = "-1";
		this.idTipologiaIntervento = "-1";
		this.idStatoProgetto = "-1";
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
		toString = toString + "idAnnoAggregatos: (" + idAnnoAggregatos + "); ";
		toString = toString + "idCategoriaSoggetto: (" + idCategoriaSoggetto + "); ";
		toString = toString + "idSottoCategoriaSoggetto: (" + idSottoCategoriaSoggetto + "); ";
		toString = toString + "idTipologiaIntervento: (" + idTipologiaIntervento + "); ";
		toString = toString + "idStatoProgetto: (" + idStatoProgetto + "); ";
		toString = toString + "naviga: (" + naviga + "); ";
		toString = toString + "pagAggregata: (" + pagAggregata + "); ";
		toString = toString + "pagElencoProgetti: (" + pagElencoProgetti + "); ";
		toString = toString + "pagDettaglioProgetto" + pagDettaglioProgetto + "); ";
		toString = toString + "distribuzione: (" + distribuzione + "); ";
		toString = toString + "importaInNavigaProgetti: (" + importaInNavigaProgetti + "); ";
		
		return toString;
	}

	public boolean isFiltroClassificazione(){
		boolean retval=false;
		if( idAnnoAggregatos.size() == 1 && (!idAnnoAggregatos.contains("-1")) ){
			 retval=true;
		}else
		if( idAnnoAggregatos.size() > 1 ){
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
		if(! "-1".equals( idTipologiaIntervento ) ){
			 retval=true;
		}else
		if(! "-1".equals( idStatoProgetto ) ){
			 retval=true;
		}else
		if(! "-1".equals( idNatura ) ){
			if(NAVIGA_CLASSIFICAZIONE.equals(this.naviga) && "0".equals( idNatura ) ){
				retval=false;
			}else{
				retval=true;
			}
		}else
		if(! "-1".equals( idAreaIntervento ) ){
			if(NAVIGA_CLASSIFICAZIONE.equals(this.naviga) && "0".equals( idAreaIntervento ) ){
				retval=false;
			}else{
				retval=true;
			}
		}else
		if(! "-1".equals( idSottosettoreIntervento ) ){
			if(NAVIGA_CLASSIFICAZIONE.equals(this.naviga) && "0".equals( idSottosettoreIntervento ) ){
				retval=false;
			}else{
				retval=true;
			}
		}else
		if(! "-1".equals( idCategoriaIntervento ) ){
			if(NAVIGA_CLASSIFICAZIONE.equals(this.naviga) && "0".equals( idCategoriaIntervento ) ){
				retval=false;
			}else{
				retval=true;
			}
		}
		
		return retval;
	}
	
	public String getCountAffRicerca4Natura(){
		
		String sRetval = null;
		int retval = 0;
		
		if( idAnnoAggregatos.size() == 1 && (!idAnnoAggregatos.contains("-1")) ){
			 retval++;
		}else
		if( idAnnoAggregatos.size() > 1 ){
			retval++;
		}
		
		if(! "-1".equals( idRegione ) ){
			 retval++;
		}
		
		if(! "-1".equals( idProvincia ) ){
			 retval++;
		}
		
		if(! "-1".equals( idComune ) ){
			 retval++;
		}
		
		if(! "-1".equals( idAreaGeografica ) ){
			 retval++;
		}

		if(! "-1".equals( idCategoriaSoggetto ) ){
			 retval++;
		}
		
		if(! "-1".equals( idSottoCategoriaSoggetto ) ){
			 retval++;
		}
		
		if(! "-1".equals( idTipologiaIntervento ) ){
			 retval++;
		}
		
		if(! "-1".equals( idStatoProgetto ) ){
			 retval++;
		}
		
		if( retval > 0 ){
			sRetval = String.valueOf(retval);
		}
		
		return sRetval;
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


	public boolean isImportaInNavigaProgetti() {
		return importaInNavigaProgetti;
	}

	public void setImportaInNavigaProgetti(boolean importaInNavigaProgetti) {
		this.importaInNavigaProgetti = importaInNavigaProgetti;
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

	public List<String> getIdAnnoAggregatos() {
		return idAnnoAggregatos;
	}

	public void setIdAnnoAggregatos(List<String> idAnnoAggregatos) {
		this.idAnnoAggregatos = idAnnoAggregatos;
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

	public String getDescNatura() {
		return descNatura;
	}

	public void setDescNatura(String descNatura) {
		this.descNatura = descNatura;
	}

	public String getDescAreaIntervento() {
		return descAreaIntervento;
	}

	public void setDescAreaIntervento(String descAreaIntervento) {
		this.descAreaIntervento = descAreaIntervento;
	}

	public String getDescSottosettoreIntervento() {
		return descSottosettoreIntervento;
	}

	public void setDescSottosettoreIntervento(String descSottosettoreIntervento) {
		this.descSottosettoreIntervento = descSottosettoreIntervento;
	}

	public String getDescCategoriaIntervento() {
		return descCategoriaIntervento;
	}

	public void setDescCategoriaIntervento(String descCategoriaIntervento) {
		this.descCategoriaIntervento = descCategoriaIntervento;
	}

	public String getDescAnnoAggregatos() {
		return descAnnoAggregatos;
	}

	public void setDescAnnoAggregatos(String descAnnoAggregatos) {
		this.descAnnoAggregatos = descAnnoAggregatos;
	}

	public String getDescRegione() {
		return descRegione;
	}

	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}

	public String getDescProvincia() {
		return descProvincia;
	}

	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}

	public String getDescComune() {
		return descComune;
	}

	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}

	public String getDescAreaGeografica() {
		return descAreaGeografica;
	}

	public void setDescAreaGeografica(String descAreaGeografica) {
		this.descAreaGeografica = descAreaGeografica;
	}

	public String getDescCategoriaSoggetto() {
		return descCategoriaSoggetto;
	}

	public void setDescCategoriaSoggetto(String descCategoriaSoggetto) {
		this.descCategoriaSoggetto = descCategoriaSoggetto;
	}

	public String getDescSottoCategoriaSoggetto() {
		return descSottoCategoriaSoggetto;
	}

	public void setDescSottoCategoriaSoggetto(String descSottoCategoriaSoggetto) {
		this.descSottoCategoriaSoggetto = descSottoCategoriaSoggetto;
	}

	public String getDescTipologiaIntervento() {
		return descTipologiaIntervento;
	}

	public void setDescTipologiaIntervento(String descTipologiaIntervento) {
		this.descTipologiaIntervento = descTipologiaIntervento;
	}

	public String getDescStatoProgetto() {
		return descStatoProgetto;
	}

	public void setDescStatoProgetto(String descStatoProgetto) {
		this.descStatoProgetto = descStatoProgetto;
	}

	public String getDistribuzione() {
		return distribuzione;
	}

	public void setDistribuzione(String distribuzione) {
		this.distribuzione = distribuzione;
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

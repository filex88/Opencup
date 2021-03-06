package it.dipe.opencup.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigaAggregata implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 143973242554717243L;
	
	public static final String NAVIGA_CLASSIFICAZIONE = "classificazione";
	public static final String NAVIGA_LOCALIZZAZIONE = "localizzazione";
	
	private String pagAggregata;
	private String indicatoreNavigaLocalizzazione;
	
	private String naviga;
	private String distribuzione;
	
	private String orderType;
	private String orderProperty;
	
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
	private String idAreaSoggetto;
	
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
	
	private boolean flagAggregaAnni=true;
	
	private boolean filtroClassificazione;
	
	private String countAffRicercaLocalizzazione;
	
	private String countAffRicerca4Natura;

	public void setCountAffRicerca4Natura(String countAffRicerca4Natura) {
		this.countAffRicerca4Natura = countAffRicerca4Natura;
	}

	public void importa(NavigaProgetti navigaProgetti) {
		this.pagAggregata = navigaProgetti.getPagAggregata();
		
		this.naviga = navigaProgetti.getNaviga();
		this.idNatura = navigaProgetti.getIdNatura();
		this.idAreaIntervento = navigaProgetti.getIdAreaIntervento();
		this.idSottosettoreIntervento = navigaProgetti.getIdSottosettoreIntervento();
		this.idCategoriaIntervento = navigaProgetti.getIdCategoriaIntervento();
		
		this.idAreaSoggetto = navigaProgetti.getIdAreaSoggetto();
		this.idCategoriaSoggetto = navigaProgetti.getIdCategoriaSoggetto();
		this.idSottoCategoriaSoggetto = navigaProgetti.getIdSottoCategoriaSoggetto();
		
		this.idTipologiaIntervento = navigaProgetti.getIdTipologiaIntervento();
		this.idStatoProgetto = navigaProgetti.getIdStatoProgetto();
		this.idAreaGeografica = navigaProgetti.getIdAreaGeografica();
		this.descStato = navigaProgetti.getDescStato();
		this.idRegione = navigaProgetti.getIdRegione();
		this.idProvincia = navigaProgetti.getIdProvincia();
		this.idComune = navigaProgetti.getIdComune();
		
		this.idAnnoAggregatos = new ArrayList<String>();
		this.idAnnoAggregatos.add("0");
		
		this.indicatoreNavigaLocalizzazione = "A";
	}
	
	public void rimuoviZero() {
		//CLASSIFICAZIONE
		if( this.getIdNatura().equals("0") ){
			this.setIdNatura("-1");
		}
		if( this.getIdAreaIntervento().equals("0") ){
			this.setIdAreaIntervento("-1");
		}
		if( this.getIdSottosettoreIntervento().equals("0") ){
			this.setIdSottosettoreIntervento("-1");
		}
		if( this.getIdCategoriaIntervento().equals("0") ){
			this.setIdCategoriaIntervento("-1");
		}
		
		//LOCALIZZAZIONE
		if( this.getIdRegione().equals("0") ){
			this.setIdRegione("-1");
		}
		if( this.getIdProvincia().equals("0") ){
			this.setIdProvincia("-1");
		}
		if( this.getIdComune().equals("0") ){
			this.setIdComune("-1");
		}
		if( this.getIdAreaGeografica().equals("0") ){
			this.setIdAreaGeografica("-1");
		}

		//OTHERS
		if( this.getIdCategoriaSoggetto().equals("0") ){
			this.setIdCategoriaSoggetto("-1");
		}
		if( this.getIdSottoCategoriaSoggetto().equals("0") ){
			this.setIdSottoCategoriaSoggetto("-1");
		}
		if( this.getIdAreaSoggetto().equals("0") ){
			this.setIdAreaSoggetto("-1");
		}
		if( this.getIdTipologiaIntervento().equals("0") ){
			this.setIdTipologiaIntervento("-1");
		}
		if( this.getIdStatoProgetto().equals("0") ){
			this.setIdStatoProgetto("-1");
		}		
	}
	
	public NavigaAggregata(String naviga, String id){
		this.naviga = naviga;
		this.distribuzione = "VOLUME";
		
		this.idNatura = id;
		this.idAreaIntervento = "-1";
		this.pagAggregata = "";
		this.indicatoreNavigaLocalizzazione = "A";
		
		/*
		if(NAVIGA_CLASSIFICAZIONE.equals(this.naviga)){
			this.idNatura = id;
			this.idAreaIntervento = "0";
			this.pagAggregata = "natura";
			this.pagElencoProgetti = "natelencoprogetti";
			this.pagDettaglioProgetto = "natdettaglioprogetto";
		}
		else if(NAVIGA_LOCALIZZAZIONE.equals(this.naviga)){
			this.idNatura = "-1";
			this.idAreaIntervento = "-1";
			this.pagAggregata = "localizzazione";
			this.pagElencoProgetti = "locelencoprogetti";
			this.pagDettaglioProgetto = "locdettaglioprogetto";
		}
		
		else{
			this.idNatura = "-1";
			this.idAreaIntervento = "-1";
		}
		*/
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
		this.idAreaSoggetto = "-1";
		this.idTipologiaIntervento = "-1";
		this.idStatoProgetto = "-1";
	}

	public NavigaAggregata(){
		this.distribuzione = "VOLUME";
		this.indicatoreNavigaLocalizzazione = "A";
		
		this.pagAggregata = "";
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
		this.idAreaSoggetto = "-1";
		this.idTipologiaIntervento = "-1";
		this.idStatoProgetto = "-1";
	}
	
	public String toString(){
		String toString = "";
		toString = toString + "pagAggregata: (" + pagAggregata + "); ";
		toString = toString + "indicatoreNavigaLocalizzazione: (" + indicatoreNavigaLocalizzazione + "); ";
		toString = toString + "naviga: (" + naviga + "); ";
		toString = toString + "distribuzione: (" + distribuzione + "); ";
		toString = toString + "orderType: (" + orderType + "); ";
		toString = toString + "orderProperty: (" + orderProperty + "); ";
		toString = toString + "idNatura: (" + idNatura + "); ";
		toString = toString + "idAreaIntervento: (" + idAreaIntervento + "); ";
		toString = toString + "idSottosettoreIntervento: (" + idSottosettoreIntervento + "); ";
		toString = toString + "idCategoriaIntervento: (" + idCategoriaIntervento + "); ";
		toString = toString + "idAnnoAggregatos: (" + idAnnoAggregatos + "); ";
		toString = toString + "idRegione: (" + idRegione + "); ";
		toString = toString + "idProvincia: (" + idProvincia + "); ";
		toString = toString + "idComune: (" + idComune + "); ";
		toString = toString + "idAreaGeografica: (" + idAreaGeografica + "); ";
		toString = toString + "descStato: (" + descStato + "); ";
		toString = toString + "idCategoriaSoggetto: (" + idCategoriaSoggetto + "); ";
		toString = toString + "idSottoCategoriaSoggetto: (" + idSottoCategoriaSoggetto + "); ";
		toString = toString + "idAreaSoggetto: (" + idAreaSoggetto + "); ";
		toString = toString + "idTipologiaIntervento: (" + idTipologiaIntervento + "); ";
		toString = toString + "idStatoProgetto: (" + idStatoProgetto + "); ";
		toString = toString + "descNatura: (" + descNatura + "); ";
		toString = toString + "descAreaIntervento: (" + descAreaIntervento + "); ";
		toString = toString + "descSottosettoreIntervento: (" + descSottosettoreIntervento + ")"; ;
		toString = toString + "descCategoriaIntervento: (" + descCategoriaIntervento + "); ";
		toString = toString + "descAnnoAggregatos: (" + descAnnoAggregatos + "); ";
		toString = toString + "descRegione: (" + descRegione + "); ";
		toString = toString + "descProvincia: (" + descProvincia + "); ";
		toString = toString + "descComune: (" + descComune + "); ";
		toString = toString + "descAreaGeografica: (" + descAreaGeografica + "); ";
		toString = toString + "descCategoriaSoggetto: (" + descCategoriaSoggetto + "); ";
		toString = toString + "descSottoCategoriaSoggetto: (" + descSottoCategoriaSoggetto + "); ";
		toString = toString + "descTipologiaIntervento: (" + descTipologiaIntervento + "); ";
		toString = toString + "descStatoProgetto: (" + descStatoProgetto + "); ";
		toString = toString + " flagAggregaAnni: (" + flagAggregaAnni + "); ";
		toString = toString + " filtroClassificazione: (" + filtroClassificazione + "); ";
		toString = toString + "countAffRicercaLocalizzazione: (" + countAffRicercaLocalizzazione + "); ";
		toString = toString + "countAffRicerca4Natura: (" + countAffRicerca4Natura + "); ";
		return toString;
	}
	
	public NavigaAggregata clone() throws CloneNotSupportedException {
        return (NavigaAggregata) super.clone();
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
		if(! "-1".equals( idAreaSoggetto ) ){
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
		
		if(! "-1".equals( idAreaSoggetto ) ){
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

	public String getCountAffRicercaLocalizzazione(){
		
		String sRetval = null;
		int retval = 0;
		
		if( idAnnoAggregatos.size() == 1 && (!idAnnoAggregatos.contains("-1")) ){
			 retval++;
		}else
		if( idAnnoAggregatos.size() > 1 ){
			retval++;
		}
		
		if(! "-1".equals( idAreaIntervento ) ){
			 retval++;
		}
		
		if(! "-1".equals( idSottosettoreIntervento ) ){
			 retval++;
		}
		
		if(! "-1".equals( idCategoriaIntervento ) ){
			 retval++;
		}
		
		

		if(! "-1".equals( idCategoriaSoggetto ) ){
			 retval++;
		}
		
		if(! "-1".equals( idSottoCategoriaSoggetto ) ){
			 retval++;
		}
		
		if(! "-1".equals( idAreaSoggetto ) ){
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

	public String getIdAreaSoggetto() {
		return idAreaSoggetto;
	}

	public void setIdAreaSoggetto(String idAreaSoggetto) {
		this.idAreaSoggetto = idAreaSoggetto;
	}
	
	
	public void setFiltroClassificazione(boolean filtroClassificazione) {
		this.filtroClassificazione = filtroClassificazione;
	}
	
	
	public void setCountAffRicercaLocalizzazione(
			String countAffRicercaLocalizzazione) {
		this.countAffRicercaLocalizzazione = countAffRicercaLocalizzazione;
	}

	public boolean isFlagAggregaAnni() {
		return flagAggregaAnni;
	}

	public void setFlagAggregaAnni(boolean flagAggregaAnni) {
		this.flagAggregaAnni = flagAggregaAnni;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderProperty() {
		return orderProperty;
	}

	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

	public String getIndicatoreNavigaLocalizzazione() {
		return indicatoreNavigaLocalizzazione;
	}

	public void setIndicatoreNavigaLocalizzazione(
			String indicatoreNavigaLocalizzazione) {
		this.indicatoreNavigaLocalizzazione = indicatoreNavigaLocalizzazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((countAffRicerca4Natura == null) ? 0
						: countAffRicerca4Natura.hashCode());
		result = prime
				* result
				+ ((countAffRicercaLocalizzazione == null) ? 0
						: countAffRicercaLocalizzazione.hashCode());
		result = prime
				* result
				+ ((descAnnoAggregatos == null) ? 0 : descAnnoAggregatos
						.hashCode());
		result = prime
				* result
				+ ((descAreaGeografica == null) ? 0 : descAreaGeografica
						.hashCode());
		result = prime
				* result
				+ ((descAreaIntervento == null) ? 0 : descAreaIntervento
						.hashCode());
		result = prime
				* result
				+ ((descCategoriaIntervento == null) ? 0
						: descCategoriaIntervento.hashCode());
		result = prime
				* result
				+ ((descCategoriaSoggetto == null) ? 0 : descCategoriaSoggetto
						.hashCode());
		result = prime * result
				+ ((descComune == null) ? 0 : descComune.hashCode());
		result = prime * result
				+ ((descNatura == null) ? 0 : descNatura.hashCode());
		result = prime * result
				+ ((descProvincia == null) ? 0 : descProvincia.hashCode());
		result = prime * result
				+ ((descRegione == null) ? 0 : descRegione.hashCode());
		result = prime
				* result
				+ ((descSottoCategoriaSoggetto == null) ? 0
						: descSottoCategoriaSoggetto.hashCode());
		result = prime
				* result
				+ ((descSottosettoreIntervento == null) ? 0
						: descSottosettoreIntervento.hashCode());
		result = prime * result
				+ ((descStato == null) ? 0 : descStato.hashCode());
		result = prime
				* result
				+ ((descStatoProgetto == null) ? 0 : descStatoProgetto
						.hashCode());
		result = prime
				* result
				+ ((descTipologiaIntervento == null) ? 0
						: descTipologiaIntervento.hashCode());
		result = prime * result
				+ ((distribuzione == null) ? 0 : distribuzione.hashCode());
		result = prime * result + (filtroClassificazione ? 1231 : 1237);
		result = prime * result + (flagAggregaAnni ? 1231 : 1237);
		result = prime
				* result
				+ ((idAnnoAggregatos == null) ? 0 : idAnnoAggregatos.hashCode());
		result = prime
				* result
				+ ((idAreaGeografica == null) ? 0 : idAreaGeografica.hashCode());
		result = prime
				* result
				+ ((idAreaIntervento == null) ? 0 : idAreaIntervento.hashCode());
		result = prime * result
				+ ((idAreaSoggetto == null) ? 0 : idAreaSoggetto.hashCode());
		result = prime
				* result
				+ ((idCategoriaIntervento == null) ? 0 : idCategoriaIntervento
						.hashCode());
		result = prime
				* result
				+ ((idCategoriaSoggetto == null) ? 0 : idCategoriaSoggetto
						.hashCode());
		result = prime * result
				+ ((idComune == null) ? 0 : idComune.hashCode());
		result = prime * result
				+ ((idNatura == null) ? 0 : idNatura.hashCode());
		result = prime * result
				+ ((idProvincia == null) ? 0 : idProvincia.hashCode());
		result = prime * result
				+ ((idRegione == null) ? 0 : idRegione.hashCode());
		result = prime
				* result
				+ ((idSottoCategoriaSoggetto == null) ? 0
						: idSottoCategoriaSoggetto.hashCode());
		result = prime
				* result
				+ ((idSottosettoreIntervento == null) ? 0
						: idSottosettoreIntervento.hashCode());
		result = prime * result
				+ ((idStatoProgetto == null) ? 0 : idStatoProgetto.hashCode());
		result = prime
				* result
				+ ((idTipologiaIntervento == null) ? 0 : idTipologiaIntervento
						.hashCode());
		result = prime
				* result
				+ ((indicatoreNavigaLocalizzazione == null) ? 0
						: indicatoreNavigaLocalizzazione.hashCode());
		result = prime * result + ((naviga == null) ? 0 : naviga.hashCode());
		result = prime * result
				+ ((orderProperty == null) ? 0 : orderProperty.hashCode());
		result = prime * result
				+ ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result
				+ ((pagAggregata == null) ? 0 : pagAggregata.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NavigaAggregata other = (NavigaAggregata) obj;
		if (countAffRicerca4Natura == null) {
			if (other.countAffRicerca4Natura != null)
				return false;
		} else if (!countAffRicerca4Natura.equals(other.countAffRicerca4Natura))
			return false;
		if (countAffRicercaLocalizzazione == null) {
			if (other.countAffRicercaLocalizzazione != null)
				return false;
		} else if (!countAffRicercaLocalizzazione
				.equals(other.countAffRicercaLocalizzazione))
			return false;
		if (descAnnoAggregatos == null) {
			if (other.descAnnoAggregatos != null)
				return false;
		} else if (!descAnnoAggregatos.equals(other.descAnnoAggregatos))
			return false;
		if (descAreaGeografica == null) {
			if (other.descAreaGeografica != null)
				return false;
		} else if (!descAreaGeografica.equals(other.descAreaGeografica))
			return false;
		if (descAreaIntervento == null) {
			if (other.descAreaIntervento != null)
				return false;
		} else if (!descAreaIntervento.equals(other.descAreaIntervento))
			return false;
		if (descCategoriaIntervento == null) {
			if (other.descCategoriaIntervento != null)
				return false;
		} else if (!descCategoriaIntervento
				.equals(other.descCategoriaIntervento))
			return false;
		if (descCategoriaSoggetto == null) {
			if (other.descCategoriaSoggetto != null)
				return false;
		} else if (!descCategoriaSoggetto.equals(other.descCategoriaSoggetto))
			return false;
		if (descComune == null) {
			if (other.descComune != null)
				return false;
		} else if (!descComune.equals(other.descComune))
			return false;
		if (descNatura == null) {
			if (other.descNatura != null)
				return false;
		} else if (!descNatura.equals(other.descNatura))
			return false;
		if (descProvincia == null) {
			if (other.descProvincia != null)
				return false;
		} else if (!descProvincia.equals(other.descProvincia))
			return false;
		if (descRegione == null) {
			if (other.descRegione != null)
				return false;
		} else if (!descRegione.equals(other.descRegione))
			return false;
		if (descSottoCategoriaSoggetto == null) {
			if (other.descSottoCategoriaSoggetto != null)
				return false;
		} else if (!descSottoCategoriaSoggetto
				.equals(other.descSottoCategoriaSoggetto))
			return false;
		if (descSottosettoreIntervento == null) {
			if (other.descSottosettoreIntervento != null)
				return false;
		} else if (!descSottosettoreIntervento
				.equals(other.descSottosettoreIntervento))
			return false;
		if (descStato == null) {
			if (other.descStato != null)
				return false;
		} else if (!descStato.equals(other.descStato))
			return false;
		if (descStatoProgetto == null) {
			if (other.descStatoProgetto != null)
				return false;
		} else if (!descStatoProgetto.equals(other.descStatoProgetto))
			return false;
		if (descTipologiaIntervento == null) {
			if (other.descTipologiaIntervento != null)
				return false;
		} else if (!descTipologiaIntervento
				.equals(other.descTipologiaIntervento))
			return false;
		if (distribuzione == null) {
			if (other.distribuzione != null)
				return false;
		} else if (!distribuzione.equals(other.distribuzione))
			return false;
		if (filtroClassificazione != other.filtroClassificazione)
			return false;
		if (flagAggregaAnni != other.flagAggregaAnni)
			return false;
		if (idAnnoAggregatos == null) {
			if (other.idAnnoAggregatos != null)
				return false;
		} else if (!idAnnoAggregatos.equals(other.idAnnoAggregatos))
			return false;
		if (idAreaGeografica == null) {
			if (other.idAreaGeografica != null)
				return false;
		} else if (!idAreaGeografica.equals(other.idAreaGeografica))
			return false;
		if (idAreaIntervento == null) {
			if (other.idAreaIntervento != null)
				return false;
		} else if (!idAreaIntervento.equals(other.idAreaIntervento))
			return false;
		if (idAreaSoggetto == null) {
			if (other.idAreaSoggetto != null)
				return false;
		} else if (!idAreaSoggetto.equals(other.idAreaSoggetto))
			return false;
		if (idCategoriaIntervento == null) {
			if (other.idCategoriaIntervento != null)
				return false;
		} else if (!idCategoriaIntervento.equals(other.idCategoriaIntervento))
			return false;
		if (idCategoriaSoggetto == null) {
			if (other.idCategoriaSoggetto != null)
				return false;
		} else if (!idCategoriaSoggetto.equals(other.idCategoriaSoggetto))
			return false;
		if (idComune == null) {
			if (other.idComune != null)
				return false;
		} else if (!idComune.equals(other.idComune))
			return false;
		if (idNatura == null) {
			if (other.idNatura != null)
				return false;
		} else if (!idNatura.equals(other.idNatura))
			return false;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		if (idRegione == null) {
			if (other.idRegione != null)
				return false;
		} else if (!idRegione.equals(other.idRegione))
			return false;
		if (idSottoCategoriaSoggetto == null) {
			if (other.idSottoCategoriaSoggetto != null)
				return false;
		} else if (!idSottoCategoriaSoggetto
				.equals(other.idSottoCategoriaSoggetto))
			return false;
		if (idSottosettoreIntervento == null) {
			if (other.idSottosettoreIntervento != null)
				return false;
		} else if (!idSottosettoreIntervento
				.equals(other.idSottosettoreIntervento))
			return false;
		if (idStatoProgetto == null) {
			if (other.idStatoProgetto != null)
				return false;
		} else if (!idStatoProgetto.equals(other.idStatoProgetto))
			return false;
		if (idTipologiaIntervento == null) {
			if (other.idTipologiaIntervento != null)
				return false;
		} else if (!idTipologiaIntervento.equals(other.idTipologiaIntervento))
			return false;
		if (indicatoreNavigaLocalizzazione == null) {
			if (other.indicatoreNavigaLocalizzazione != null)
				return false;
		} else if (!indicatoreNavigaLocalizzazione
				.equals(other.indicatoreNavigaLocalizzazione))
			return false;
		if (naviga == null) {
			if (other.naviga != null)
				return false;
		} else if (!naviga.equals(other.naviga))
			return false;
		if (orderProperty == null) {
			if (other.orderProperty != null)
				return false;
		} else if (!orderProperty.equals(other.orderProperty))
			return false;
		if (orderType == null) {
			if (other.orderType != null)
				return false;
		} else if (!orderType.equals(other.orderType))
			return false;
		if (pagAggregata == null) {
			if (other.pagAggregata != null)
				return false;
		} else if (!pagAggregata.equals(other.pagAggregata))
			return false;
		return true;
	}
}

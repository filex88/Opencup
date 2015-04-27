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
	private String idAreaSoggetto;
	
	private String idRegione;
	private String idProvincia;
	private String idComune;
	
	public void importa(NavigaAggregata navigaAggregata) {
		
		this.pagAggregata = navigaAggregata.getPagAggregata();
		
		this.naviga = navigaAggregata.getNaviga();
		this.idNatura = navigaAggregata.getIdNatura();
		this.idAreaIntervento = navigaAggregata.getIdAreaIntervento();
		this.idSottosettoreIntervento = navigaAggregata.getIdSottosettoreIntervento();
		this.idCategoriaIntervento = navigaAggregata.getIdCategoriaIntervento();
		
		this.idAreaSoggetto = navigaAggregata.getIdAreaSoggetto();
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
	
	public void setCountAffRicerca(String countAffRicerca){
		
	}
	public String getCountAffRicerca(){
		
		String sRetval = null;
		int retval = 0;

		if(! "-1".equals( idAreaIntervento ) ){
			 retval++;
		}

		if(! "-1".equals( idSottosettoreIntervento ) ){
			 retval++;
		}

		if(! "-1".equals( idCategoriaIntervento ) ){
			 retval++;
		}
		
		if( idAnnoDecisiones.size() == 1 && (!idAnnoDecisiones.contains("-1")) ){
			 retval++;
		}else
		if( idAnnoDecisiones.size() > 1 ){
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
	
	public NavigaProgetti(){
		this.pagAggregata = "";
		this.naviga = "";
		this.idNatura = "-1";
		this.idAreaIntervento = "-1";
		this.idSottosettoreIntervento = "-1";
		this.idCategoriaIntervento = "-1";
		this.idAnnoDecisiones = new ArrayList<String>();
		this.idAnnoDecisiones.add("-1");
		this.idCategoriaSoggetto = "-1";
		this.idSottoCategoriaSoggetto = "-1";
		this.idAreaSoggetto = "-1";
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
		toString = toString + "idAreaSoggetto: (" + idAreaSoggetto + "); ";
		toString = toString + "idTipologiaIntervento: (" + idTipologiaIntervento + "); ";
		toString = toString + "idStatoProgetto: (" + idStatoProgetto + "); ";
		toString = toString + "naviga: (" + naviga + "); ";
		toString = toString + "pagAggregata: (" + pagAggregata + "); ";
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

	public String getIdAreaSoggetto() {
		return idAreaSoggetto;
	}

	public void setIdAreaSoggetto(String idAreaSoggetto) {
		this.idAreaSoggetto = idAreaSoggetto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descStato == null) ? 0 : descStato.hashCode());
		result = prime
				* result
				+ ((idAnnoDecisiones == null) ? 0 : idAnnoDecisiones.hashCode());
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
				+ ((idProgetto == null) ? 0 : idProgetto.hashCode());
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
		result = prime * result + ((naviga == null) ? 0 : naviga.hashCode());
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
		NavigaProgetti other = (NavigaProgetti) obj;
		if (descStato == null) {
			if (other.descStato != null)
				return false;
		} else if (!descStato.equals(other.descStato))
			return false;
		if (idAnnoDecisiones == null) {
			if (other.idAnnoDecisiones != null)
				return false;
		} else if (!idAnnoDecisiones.equals(other.idAnnoDecisiones))
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
		if (idProgetto == null) {
			if (other.idProgetto != null)
				return false;
		} else if (!idProgetto.equals(other.idProgetto))
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
		if (naviga == null) {
			if (other.naviga != null)
				return false;
		} else if (!naviga.equals(other.naviga))
			return false;
		if (pagAggregata == null) {
			if (other.pagAggregata != null)
				return false;
		} else if (!pagAggregata.equals(other.pagAggregata))
			return false;
		return true;
	}
	
}

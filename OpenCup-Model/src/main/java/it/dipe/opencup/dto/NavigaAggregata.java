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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descStato == null) ? 0 : descStato.hashCode());
		result = prime * result
				+ ((idAnnoDecisione == null) ? 0 : idAnnoDecisione.hashCode());
		result = prime
				* result
				+ ((idAnnoDecisiones == null) ? 0 : idAnnoDecisiones.hashCode());
		result = prime
				* result
				+ ((idAreaGeografica == null) ? 0 : idAreaGeografica.hashCode());
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
				+ ((idSettoreInternvanto == null) ? 0 : idSettoreInternvanto
						.hashCode());
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
				+ ((idTipologiaInterventi == null) ? 0 : idTipologiaInterventi
						.hashCode());
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
		if (descStato == null) {
			if (other.descStato != null)
				return false;
		} else if (!descStato.equals(other.descStato))
			return false;
		if (idAnnoDecisione == null) {
			if (other.idAnnoDecisione != null)
				return false;
		} else if (!idAnnoDecisione.equals(other.idAnnoDecisione))
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
		if (idSettoreInternvanto == null) {
			if (other.idSettoreInternvanto != null)
				return false;
		} else if (!idSettoreInternvanto.equals(other.idSettoreInternvanto))
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
		if (idTipologiaInterventi == null) {
			if (other.idTipologiaInterventi != null)
				return false;
		} else if (!idTipologiaInterventi.equals(other.idTipologiaInterventi))
			return false;
		return true;
	}
	
}

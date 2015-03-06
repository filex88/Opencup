package it.dipe.opencup.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigaProgetti implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8974832497625407563L;
	
	private String idNatura;
	private String idSettoreIntervento;
	private String idSottosettoreIntervento;
	private String idCategoriaIntervento;
	
	private List<String> idAnnoDecisiones;
	
	private String textStato;
	private String textRegione;
	private String textProvincia;
	private String textComune;
	
	private String idTipologiaIntervento;
	private String idStatoProgetto;
	
	private String idCategoriaSoggetto;
	private String idSottoCategoriaSoggetto;
	
	public NavigaProgetti(){
		this.idNatura = "-1";
		this.idSettoreIntervento = "-1";
		this.idSottosettoreIntervento = "-1";
		this.idCategoriaIntervento = "-1";
		this.idAnnoDecisiones = new ArrayList<String>();
		this.idAnnoDecisiones.add("-1");
		this.textRegione = "";
		this.textProvincia = "";
		this.textComune = "";
		this.textStato = "ITALIA";
		this.idCategoriaSoggetto = "-1";
		this.idSottoCategoriaSoggetto = "-1";
		this.idTipologiaIntervento = "-1";
		this.idStatoProgetto = "-1";
	}

	public String getIdNatura() {
		return idNatura;
	}

	public void setIdNatura(String idNatura) {
		this.idNatura = idNatura;
	}

	public String getIdSettoreIntervento() {
		return idSettoreIntervento;
	}

	public void setIdSettoreIntervento(String idSettoreIntervento) {
		this.idSettoreIntervento = idSettoreIntervento;
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

	public String getTextStato() {
		return textStato;
	}

	public void setTextStato(String textStato) {
		this.textStato = textStato;
	}

	public String getTextRegione() {
		return textRegione;
	}

	public void setTextRegione(String textRegione) {
		this.textRegione = textRegione;
	}

	public String getTextProvincia() {
		return textProvincia;
	}

	public void setTextProvincia(String textProvincia) {
		this.textProvincia = textProvincia;
	}

	public String getTextComune() {
		return textComune;
	}

	public void setTextComune(String textComune) {
		this.textComune = textComune;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idAnnoDecisiones == null) ? 0 : idAnnoDecisiones.hashCode());
		result = prime
				* result
				+ ((idCategoriaIntervento == null) ? 0 : idCategoriaIntervento
						.hashCode());
		result = prime
				* result
				+ ((idCategoriaSoggetto == null) ? 0 : idCategoriaSoggetto
						.hashCode());
		result = prime * result
				+ ((idNatura == null) ? 0 : idNatura.hashCode());
		result = prime
				* result
				+ ((idSettoreIntervento == null) ? 0 : idSettoreIntervento
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
				+ ((idTipologiaIntervento == null) ? 0 : idTipologiaIntervento
						.hashCode());
		result = prime * result
				+ ((textComune == null) ? 0 : textComune.hashCode());
		result = prime * result
				+ ((textProvincia == null) ? 0 : textProvincia.hashCode());
		result = prime * result
				+ ((textRegione == null) ? 0 : textRegione.hashCode());
		result = prime * result
				+ ((textStato == null) ? 0 : textStato.hashCode());
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
		if (idAnnoDecisiones == null) {
			if (other.idAnnoDecisiones != null)
				return false;
		} else if (!idAnnoDecisiones.equals(other.idAnnoDecisiones))
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
		if (idNatura == null) {
			if (other.idNatura != null)
				return false;
		} else if (!idNatura.equals(other.idNatura))
			return false;
		if (idSettoreIntervento == null) {
			if (other.idSettoreIntervento != null)
				return false;
		} else if (!idSettoreIntervento.equals(other.idSettoreIntervento))
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
		if (textComune == null) {
			if (other.textComune != null)
				return false;
		} else if (!textComune.equals(other.textComune))
			return false;
		if (textProvincia == null) {
			if (other.textProvincia != null)
				return false;
		} else if (!textProvincia.equals(other.textProvincia))
			return false;
		if (textRegione == null) {
			if (other.textRegione != null)
				return false;
		} else if (!textRegione.equals(other.textRegione))
			return false;
		if (textStato == null) {
			if (other.textStato != null)
				return false;
		} else if (!textStato.equals(other.textStato))
			return false;
		return true;
	}
	
	
	

}

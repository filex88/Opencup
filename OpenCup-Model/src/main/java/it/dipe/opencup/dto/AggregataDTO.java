package it.dipe.opencup.dto;

import it.dipe.opencup.model.Aggregata;

import java.io.Serializable;


public class AggregataDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162835602618677174L;

	private Integer id;

	private Long numeProgetti;

	private Double impoCostoProgetti;

	private Double impoImportoFinanziato;

	private String annoAnnoAggregato;

	private Integer idNatura;

	private String desNatura;

	private String codNatura;

	private Integer idArea;

	private String desArea;

	private String codArea;

	private Integer idSottoSettore;

	private String desSottoSettore;

	private String codSottoSettore;

	private Integer idCategoriaIntervento;

	private String desCategoriaIntervento;

	private String codCategoriaIntervento;

	private String linkURL;
	
	private String descURL;
	
	private Integer idStatoProgetto;
	
	private String codStatoProgetto;
	
	private String desStatoProgetto;
	
	private String desDecodStatoProgetto;
	
	private Integer idAreaSoggetto;
	
	private Integer idCategSoggetto;
	
	private Integer idSottocategSoggetto;
	
	private String descAreaSoggetto;
	
	private String descCategSoggetto;
	
	private String descSottocategSoggetto;
	
	public AggregataDTO() {

	}

	public AggregataDTO(Aggregata aggregata) {

		this.id = aggregata.getId();
		
		this.numeProgetti = aggregata.getNumeProgetti();
		this.impoCostoProgetti = aggregata.getImpoCostoProgetti();
		this.impoImportoFinanziato = aggregata.getImpoImportoFinanziato();
		
		this.annoAnnoAggregato = aggregata.getAnnoAggregato().getAnnoAggregato();
		
		this.idNatura = aggregata.getClassificazione().getNatura().getId();
		this.desNatura = aggregata.getClassificazione().getNatura().getDescNatura();
		this.codNatura = aggregata.getClassificazione().getNatura().getCodiNatura();
		
		this.idArea = aggregata.getClassificazione().getAreaIntervento().getId();
		this.desArea = aggregata.getClassificazione().getAreaIntervento().getDescAreaIntervento();
		this.codArea = aggregata.getClassificazione().getAreaIntervento().getCodiAreaIntervento();
		
		this.idSottoSettore = aggregata.getClassificazione().getSottosettoreIntervento().getId();
		this.desSottoSettore = aggregata.getClassificazione().getSottosettoreIntervento().getDescSottosettoreInt();
		this.codSottoSettore = aggregata.getClassificazione().getSottosettoreIntervento().getCodiSottosettoreInt();
		
		this.idCategoriaIntervento = aggregata.getClassificazione().getCategoriaIntervento().getId();
		this.desCategoriaIntervento = aggregata.getClassificazione().getCategoriaIntervento().getDescCategoriaIntervento();
		this.codCategoriaIntervento = aggregata.getClassificazione().getCategoriaIntervento().getCodiCategoriaIntervento();
		
		this.idStatoProgetto = aggregata.getStatoProgetto().getId();
		this.codStatoProgetto = aggregata.getStatoProgetto().getCodiStatoProgetto();
		this.desStatoProgetto = aggregata.getStatoProgetto().getDescStatoProgetto();
		this.desDecodStatoProgetto = aggregata.getStatoProgetto().getDescDecodStatoProgetto();
		
		this.idCategSoggetto = aggregata.getGerarchiaSoggetto().getCategoriaSoggetto().getId();
		this.idSottocategSoggetto = aggregata.getGerarchiaSoggetto().getSottocategoriaSoggetto().getId();
		this.idAreaSoggetto = aggregata.getGerarchiaSoggetto().getAreaSoggetto().getId();
		this.descCategSoggetto = aggregata.getGerarchiaSoggetto().getDescCategSoggetto();
		this.descSottocategSoggetto = aggregata.getGerarchiaSoggetto().getDescSottocategSoggetto();
		this.descAreaSoggetto = aggregata.getGerarchiaSoggetto().getDescAreaSoggetto();
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNumeProgetti() {
		return numeProgetti;
	}

	public void setNumeProgetti(Long numeProgetti) {
		this.numeProgetti = numeProgetti;
	}

	public Double getImpoCostoProgetti() {
		return impoCostoProgetti;
	}

	public void setImpoCostoProgetti(Double impoCostoProgetti) {
		this.impoCostoProgetti = impoCostoProgetti;
	}

	public Double getImpoImportoFinanziato() {
		return impoImportoFinanziato;
	}

	public void setImpoImportoFinanziato(Double impoImportoFinanziato) {
		this.impoImportoFinanziato = impoImportoFinanziato;
	}

	public String getAnnoAnnoAggregato() {
		return annoAnnoAggregato;
	}

	public void setAnnoAnnoAggregato(String annoAnnoAggregato) {
		this.annoAnnoAggregato = annoAnnoAggregato;
	}

	public Integer getIdNatura() {
		return idNatura;
	}

	public void setIdNatura(Integer idNatura) {
		this.idNatura = idNatura;
	}

	public String getDesNatura() {
		return desNatura;
	}

	public void setDesNatura(String desNatura) {
		this.desNatura = desNatura;
	}

	public String getCodNatura() {
		return codNatura;
	}

	public void setCodNatura(String codNatura) {
		this.codNatura = codNatura;
	}

	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public String getDesArea() {
		return desArea;
	}

	public void setDesArea(String desArea) {
		this.desArea = desArea;
	}

	public String getCodArea() {
		return codArea;
	}

	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}

	public Integer getIdSottoSettore() {
		return idSottoSettore;
	}

	public void setIdSottoSettore(Integer idSottoSettore) {
		this.idSottoSettore = idSottoSettore;
	}

	public String getDesSottoSettore() {
		return desSottoSettore;
	}

	public void setDesSottoSettore(String desSottoSettore) {
		this.desSottoSettore = desSottoSettore;
	}

	public String getCodSottoSettore() {
		return codSottoSettore;
	}

	public void setCodSottoSettore(String codSottoSettore) {
		this.codSottoSettore = codSottoSettore;
	}

	public Integer getIdCategoriaIntervento() {
		return idCategoriaIntervento;
	}

	public void setIdCategoriaIntervento(Integer idCategoriaIntervento) {
		this.idCategoriaIntervento = idCategoriaIntervento;
	}

	public String getDesCategoriaIntervento() {
		return desCategoriaIntervento;
	}

	public void setDesCategoriaIntervento(String desCategoriaIntervento) {
		this.desCategoriaIntervento = desCategoriaIntervento;
	}

	public String getCodCategoriaIntervento() {
		return codCategoriaIntervento;
	}

	public void setCodCategoriaIntervento(String codCategoriaIntervento) {
		this.codCategoriaIntervento = codCategoriaIntervento;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getDescURL() {
		return descURL;
	}

	public void setDescURL(String descURL) {
		this.descURL = descURL;
	}

	public Integer getIdStatoProgetto() {
		return idStatoProgetto;
	}

	public void setIdStatoProgetto(Integer idStatoProgetto) {
		this.idStatoProgetto = idStatoProgetto;
	}

	public String getCodStatoProgetto() {
		return codStatoProgetto;
	}

	public void setCodStatoProgetto(String codStatoProgetto) {
		this.codStatoProgetto = codStatoProgetto;
	}

	public String getDesStatoProgetto() {
		return desStatoProgetto;
	}

	public void setDesStatoProgetto(String desStatoProgetto) {
		this.desStatoProgetto = desStatoProgetto;
	}

	public String getDesDecodStatoProgetto() {
		return desDecodStatoProgetto;
	}

	public void setDesDecodStatoProgetto(String desDecodStatoProgetto) {
		this.desDecodStatoProgetto = desDecodStatoProgetto;
	}

	public Integer getIdCategSoggetto() {
		return idCategSoggetto;
	}

	public void setIdCategSoggetto(Integer idCategSoggetto) {
		this.idCategSoggetto = idCategSoggetto;
	}

	public Integer getIdSottocategSoggetto() {
		return idSottocategSoggetto;
	}

	public void setIdSottocategSoggetto(Integer idSottocategSoggetto) {
		this.idSottocategSoggetto = idSottocategSoggetto;
	}

	public Integer getIdAreaSoggetto() {
		return idAreaSoggetto;
	}

	public void setIdAreaSoggetto(Integer idAreaSoggetto) {
		this.idAreaSoggetto = idAreaSoggetto;
	}

	public String getDescCategSoggetto() {
		return descCategSoggetto;
	}

	public void setDescCategSoggetto(String descCategSoggetto) {
		this.descCategSoggetto = descCategSoggetto;
	}

	public String getDescSottocategSoggetto() {
		return descSottocategSoggetto;
	}

	public void setDescSottocategSoggetto(String descSottocategSoggetto) {
		this.descSottocategSoggetto = descSottocategSoggetto;
	}

	public String getDescAreaSoggetto() {
		return descAreaSoggetto;
	}

	public void setDescAreaSoggetto(String descAreaSoggetto) {
		this.descAreaSoggetto = descAreaSoggetto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((annoAnnoAggregato == null) ? 0 : annoAnnoAggregato
						.hashCode());
		result = prime * result + ((codArea == null) ? 0 : codArea.hashCode());
		result = prime
				* result
				+ ((codCategoriaIntervento == null) ? 0
						: codCategoriaIntervento.hashCode());
		result = prime * result
				+ ((codNatura == null) ? 0 : codNatura.hashCode());
		result = prime * result
				+ ((codSottoSettore == null) ? 0 : codSottoSettore.hashCode());
		result = prime
				* result
				+ ((codStatoProgetto == null) ? 0 : codStatoProgetto.hashCode());
		result = prime * result + ((desArea == null) ? 0 : desArea.hashCode());
		result = prime
				* result
				+ ((desCategoriaIntervento == null) ? 0
						: desCategoriaIntervento.hashCode());
		result = prime
				* result
				+ ((desDecodStatoProgetto == null) ? 0 : desDecodStatoProgetto
						.hashCode());
		result = prime * result
				+ ((desNatura == null) ? 0 : desNatura.hashCode());
		result = prime * result
				+ ((desSottoSettore == null) ? 0 : desSottoSettore.hashCode());
		result = prime
				* result
				+ ((desStatoProgetto == null) ? 0 : desStatoProgetto.hashCode());
		result = prime
				* result
				+ ((descAreaSoggetto == null) ? 0 : descAreaSoggetto.hashCode());
		result = prime
				* result
				+ ((descCategSoggetto == null) ? 0 : descCategSoggetto
						.hashCode());
		result = prime
				* result
				+ ((descSottocategSoggetto == null) ? 0
						: descSottocategSoggetto.hashCode());
		result = prime * result + ((descURL == null) ? 0 : descURL.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idArea == null) ? 0 : idArea.hashCode());
		result = prime * result
				+ ((idAreaSoggetto == null) ? 0 : idAreaSoggetto.hashCode());
		result = prime * result
				+ ((idCategSoggetto == null) ? 0 : idCategSoggetto.hashCode());
		result = prime
				* result
				+ ((idCategoriaIntervento == null) ? 0 : idCategoriaIntervento
						.hashCode());
		result = prime * result
				+ ((idNatura == null) ? 0 : idNatura.hashCode());
		result = prime * result
				+ ((idSottoSettore == null) ? 0 : idSottoSettore.hashCode());
		result = prime
				* result
				+ ((idSottocategSoggetto == null) ? 0 : idSottocategSoggetto
						.hashCode());
		result = prime * result
				+ ((idStatoProgetto == null) ? 0 : idStatoProgetto.hashCode());
		result = prime
				* result
				+ ((impoCostoProgetti == null) ? 0 : impoCostoProgetti
						.hashCode());
		result = prime
				* result
				+ ((impoImportoFinanziato == null) ? 0 : impoImportoFinanziato
						.hashCode());
		result = prime * result + ((linkURL == null) ? 0 : linkURL.hashCode());
		result = prime * result
				+ ((numeProgetti == null) ? 0 : numeProgetti.hashCode());
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
		AggregataDTO other = (AggregataDTO) obj;
		if (annoAnnoAggregato == null) {
			if (other.annoAnnoAggregato != null)
				return false;
		} else if (!annoAnnoAggregato.equals(other.annoAnnoAggregato))
			return false;
		if (codArea == null) {
			if (other.codArea != null)
				return false;
		} else if (!codArea.equals(other.codArea))
			return false;
		if (codCategoriaIntervento == null) {
			if (other.codCategoriaIntervento != null)
				return false;
		} else if (!codCategoriaIntervento.equals(other.codCategoriaIntervento))
			return false;
		if (codNatura == null) {
			if (other.codNatura != null)
				return false;
		} else if (!codNatura.equals(other.codNatura))
			return false;
		if (codSottoSettore == null) {
			if (other.codSottoSettore != null)
				return false;
		} else if (!codSottoSettore.equals(other.codSottoSettore))
			return false;
		if (codStatoProgetto == null) {
			if (other.codStatoProgetto != null)
				return false;
		} else if (!codStatoProgetto.equals(other.codStatoProgetto))
			return false;
		if (desArea == null) {
			if (other.desArea != null)
				return false;
		} else if (!desArea.equals(other.desArea))
			return false;
		if (desCategoriaIntervento == null) {
			if (other.desCategoriaIntervento != null)
				return false;
		} else if (!desCategoriaIntervento.equals(other.desCategoriaIntervento))
			return false;
		if (desDecodStatoProgetto == null) {
			if (other.desDecodStatoProgetto != null)
				return false;
		} else if (!desDecodStatoProgetto.equals(other.desDecodStatoProgetto))
			return false;
		if (desNatura == null) {
			if (other.desNatura != null)
				return false;
		} else if (!desNatura.equals(other.desNatura))
			return false;
		if (desSottoSettore == null) {
			if (other.desSottoSettore != null)
				return false;
		} else if (!desSottoSettore.equals(other.desSottoSettore))
			return false;
		if (desStatoProgetto == null) {
			if (other.desStatoProgetto != null)
				return false;
		} else if (!desStatoProgetto.equals(other.desStatoProgetto))
			return false;
		if (descAreaSoggetto == null) {
			if (other.descAreaSoggetto != null)
				return false;
		} else if (!descAreaSoggetto.equals(other.descAreaSoggetto))
			return false;
		if (descCategSoggetto == null) {
			if (other.descCategSoggetto != null)
				return false;
		} else if (!descCategSoggetto.equals(other.descCategSoggetto))
			return false;
		if (descSottocategSoggetto == null) {
			if (other.descSottocategSoggetto != null)
				return false;
		} else if (!descSottocategSoggetto.equals(other.descSottocategSoggetto))
			return false;
		if (descURL == null) {
			if (other.descURL != null)
				return false;
		} else if (!descURL.equals(other.descURL))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idArea == null) {
			if (other.idArea != null)
				return false;
		} else if (!idArea.equals(other.idArea))
			return false;
		if (idAreaSoggetto == null) {
			if (other.idAreaSoggetto != null)
				return false;
		} else if (!idAreaSoggetto.equals(other.idAreaSoggetto))
			return false;
		if (idCategSoggetto == null) {
			if (other.idCategSoggetto != null)
				return false;
		} else if (!idCategSoggetto.equals(other.idCategSoggetto))
			return false;
		if (idCategoriaIntervento == null) {
			if (other.idCategoriaIntervento != null)
				return false;
		} else if (!idCategoriaIntervento.equals(other.idCategoriaIntervento))
			return false;
		if (idNatura == null) {
			if (other.idNatura != null)
				return false;
		} else if (!idNatura.equals(other.idNatura))
			return false;
		if (idSottoSettore == null) {
			if (other.idSottoSettore != null)
				return false;
		} else if (!idSottoSettore.equals(other.idSottoSettore))
			return false;
		if (idSottocategSoggetto == null) {
			if (other.idSottocategSoggetto != null)
				return false;
		} else if (!idSottocategSoggetto.equals(other.idSottocategSoggetto))
			return false;
		if (idStatoProgetto == null) {
			if (other.idStatoProgetto != null)
				return false;
		} else if (!idStatoProgetto.equals(other.idStatoProgetto))
			return false;
		if (impoCostoProgetti == null) {
			if (other.impoCostoProgetti != null)
				return false;
		} else if (!impoCostoProgetti.equals(other.impoCostoProgetti))
			return false;
		if (impoImportoFinanziato == null) {
			if (other.impoImportoFinanziato != null)
				return false;
		} else if (!impoImportoFinanziato.equals(other.impoImportoFinanziato))
			return false;
		if (linkURL == null) {
			if (other.linkURL != null)
				return false;
		} else if (!linkURL.equals(other.linkURL))
			return false;
		if (numeProgetti == null) {
			if (other.numeProgetti != null)
				return false;
		} else if (!numeProgetti.equals(other.numeProgetti))
			return false;
		return true;
	}

}

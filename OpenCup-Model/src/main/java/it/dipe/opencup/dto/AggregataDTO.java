package it.dipe.opencup.dto;

import java.io.Serializable;

import it.dipe.opencup.model.Aggregata;


public class AggregataDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162835602618677174L;

	private Integer id;

	private Integer numeProgetti;

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

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumeProgetti() {
		return numeProgetti;
	}

	public void setNumeProgetti(Integer numeProgetti) {
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

}

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

	private String annoAnnoDecisione;

	private Integer idNatura;

	private String desNatura;

	private String codNatura;

	private Integer idSettore;

	private String desSettore;

	private String codSettore;

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
		this.annoAnnoDecisione = aggregata.getAnnoAnnoDecisione();
		
		this.idNatura = aggregata.getClassificazione().getNatura().getId();
		this.desNatura = aggregata.getClassificazione().getNatura().getDescNatura();
		this.codNatura = aggregata.getClassificazione().getNatura().getCodiNatura();
		
		this.idSettore = aggregata.getClassificazione().getSettoreInternvanto().getId();
		this.desSettore = aggregata.getClassificazione().getSettoreInternvanto().getDescSettoreIntervento();
		this.codSettore = aggregata.getClassificazione().getSettoreInternvanto().getCodiSettoreIntervento();
		
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

	public String getAnnoAnnoDecisione() {
		return annoAnnoDecisione;
	}

	public void setAnnoAnnoDecisione(String annoAnnoDecisione) {
		this.annoAnnoDecisione = annoAnnoDecisione;
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

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public Integer getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(Integer idSettore) {
		this.idSettore = idSettore;
	}

	public String getDesSettore() {
		return desSettore;
	}

	public void setDesSettore(String desSettore) {
		this.desSettore = desSettore;
	}

	public String getCodSettore() {
		return codSettore;
	}

	public void setCodSettore(String codSettore) {
		this.codSettore = codSettore;
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

	public String getDescURL() {
		return descURL;
	}

	public void setDescURL(String descURL) {
		this.descURL = descURL;
	}

}

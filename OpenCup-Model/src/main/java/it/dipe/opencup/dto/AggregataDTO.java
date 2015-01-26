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


}

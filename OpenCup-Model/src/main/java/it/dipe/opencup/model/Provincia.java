package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D_DMA_DPRO_PROVINCIA")
public class Provincia extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7964644146282342185L;

	@Id
	@Column(name = "SEQU_DPRO_ID")
	private Integer id;
	 

	@Column(name = "DESC_DPRO_PROVINCIA", length = 50)
	private String descProvincia;
	 

	@Column(name = "CODI_DPRO_PROVINCIA", length = 3)
	private String codiProvincia;
	 

	@Column(name = "TEXT_DPRO_SIGLA_PROVINCIA", length = 2)
	private String textSiglaProvincia;
	 
	
	@ManyToOne(targetEntity = Regione.class)
	@JoinColumn(name = "FK_DPRO_DREG_ID", referencedColumnName = "SEQU_DREG_ID")
	private Regione regione;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescProvincia() {
		return descProvincia;
	}


	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}


	public String getCodiProvincia() {
		return codiProvincia;
	}


	public void setCodiProvincia(String codiProvincia) {
		this.codiProvincia = codiProvincia;
	}


	public String getTextSiglaProvincia() {
		return textSiglaProvincia;
	}


	public void setTextSiglaProvincia(String textSiglaProvincia) {
		this.textSiglaProvincia = textSiglaProvincia;
	}


	public Regione getRegione() {
		return regione;
	}


	public void setRegione(Regione regione) {
		this.regione = regione;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Provincia other = (Provincia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

	
}

package it.dipe.opencup.model;

import it.dipe.opencup.model.commond.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D_DMA_DSTA_STATO")
public class Stato extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7312826089000818455L;

	@Id
	@Column(name = "SEQU_DSTA_ID")
	private Integer id;
	 

	@Column(name = "DESC_DSTA_STATO", length = 50)
	private String descStato;
	 

	@Column(name = "CODI_DSTA_STATO", length = 6)
	private String codiStato;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescStato() {
		return descStato;
	}


	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}


	public String getCodiStato() {
		return codiStato;
	}


	public void setCodiStato(String codiStato) {
		this.codiStato = codiStato;
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
		Stato other = (Stato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}

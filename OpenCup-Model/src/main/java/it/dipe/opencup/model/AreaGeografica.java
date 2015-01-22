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
@Table(name = "D_DMA_DAGE_AREA_GEOGRAFICA")
public class AreaGeografica extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -378918008203920873L;

	@Id
	@Column(name = "SEQU_DAGE_ID")
	private Integer id;

	@Column(name = "CODI_DAGE_AREA_GEOGRAFICA", length = 10)
	private String codiAreaGeografica;

	@Column(name = "DESC_DAGE_AREA_GEOGRAFICA", length = 50)
	private String descAreaGeografica;

	@ManyToOne(targetEntity = Stato.class)
	@JoinColumn(name = "FK_DAGE_DSTA_ID", referencedColumnName = "SEQU_DSTA_ID")
	private Stato stato;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiAreaGeografica() {
		return codiAreaGeografica;
	}

	public void setCodiAreaGeografica(String codiAreaGeografica) {
		this.codiAreaGeografica = codiAreaGeografica;
	}

	public String getDescAreaGeografica() {
		return descAreaGeografica;
	}

	public void setDescAreaGeografica(String descAreaGeografica) {
		this.descAreaGeografica = descAreaGeografica;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
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
		AreaGeografica other = (AreaGeografica) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

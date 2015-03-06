package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DASO_AREA_SOGGETTO")
public class AreaSoggetto extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5761306382171896484L;

	@Id
	@Column(name = "SEQU_DASO_ID")
	private Integer id;

	@Column(name = "CODI_EASO_AREA_SOGGETTO", length = 3)
	private String codiAreaSoggetto;
	
	@Column(name = "DESC_EASO_AREA_SOGGETTO", length = 100)
	private String descAreaSoggetto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiAreaSoggetto() {
		return codiAreaSoggetto;
	}

	public void setCodiAreaSoggetto(String codiAreaSoggetto) {
		this.codiAreaSoggetto = codiAreaSoggetto;
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
		AreaSoggetto other = (AreaSoggetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}

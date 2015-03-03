package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DAIN_AREA_INTERVENTO")
public class AreaIntervento extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1451160103593154584L;

	@Id
	@Column(name = "SEQU_DAIN_ID")
	private Integer id;

	@Column(name = "CODI_EAIN_AREA_INTERVENTO", length = 2)
	private String codiAreaIntervento;
	
	@Column(name = "DESC_EAIN_AREA_INTERVENTO", length = 100)
	private String descAreaIntervento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiAreaIntervento() {
		return codiAreaIntervento;
	}

	public void setCodiAreaIntervento(String codiAreaIntervento) {
		this.codiAreaIntervento = codiAreaIntervento;
	}

	public String getDescAreaIntervento() {
		return descAreaIntervento;
	}

	public void setDescAreaIntervento(String descAreaIntervento) {
		this.descAreaIntervento = descAreaIntervento;
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
		AreaIntervento other = (AreaIntervento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}

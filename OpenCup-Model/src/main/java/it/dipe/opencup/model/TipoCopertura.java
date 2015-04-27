package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DTCF_TIPO_COPERTURA_FIN")
public class TipoCopertura extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7043914734192517314L;

	@Id
	@Column(name = "SEQU_DTCF_ID")
	private Integer id;
	
	@Column(name = "CODI_DTCF_COPERTURA_FINANZ", length = 3)
	private String codiTipoCopertura;
			
	@Column(name = "DESC_DTCF_COPERTURA_FINANZ", length = 150)
	private String descTipoCopertura;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiTipoCopertura() {
		return codiTipoCopertura;
	}

	public void setCodiTipoCopertura(String codiTipoCopertura) {
		this.codiTipoCopertura = codiTipoCopertura;
	}

	public String getDescTipoCopertura() {
		return descTipoCopertura;
	}

	public void setDescTipoCopertura(String descTipoCopertura) {
		this.descTipoCopertura = descTipoCopertura;
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
		TipoCopertura other = (TipoCopertura) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}

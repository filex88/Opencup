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
@Table(name = "S_DMA_DSIN_SETTORE_INTERVENTO")
public class SettoreIntervento extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3998233129450898941L;
	
	@Id
	@Column(name = "SEQU_DSIN_ID")
	private Integer id;

	@ManyToOne(targetEntity = AreaIntervento.class)
	@JoinColumn(name = "FK_DSIN_DAIN_ID", referencedColumnName = "SEQU_DAIN_ID")
	private AreaIntervento areaIntervento;
	
	@Column(name = "CODI_DSIN_SETTORE_INTERVENTO", length = 2)
	private String codiSettoreIntervento;
	 

	@Column(name = "DESC_DSIN_SETTORE_INTERVENTO", length = 255)
	private String descSettoreIntervento;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiSettoreIntervento() {
		return codiSettoreIntervento;
	}


	public void setCodiSettoreIntervento(String codiSettoreIntervento) {
		this.codiSettoreIntervento = codiSettoreIntervento;
	}


	public String getDescSettoreIntervento() {
		return descSettoreIntervento;
	}


	public void setDescSettoreIntervento(String descSettoreIntervento) {
		this.descSettoreIntervento = descSettoreIntervento;
	}


	public AreaIntervento getAreaIntervento() {
		return areaIntervento;
	}


	public void setAreaIntervento(AreaIntervento areaIntervento) {
		this.areaIntervento = areaIntervento;
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
		SettoreIntervento other = (SettoreIntervento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

}

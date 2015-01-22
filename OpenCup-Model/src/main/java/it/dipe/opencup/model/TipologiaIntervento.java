package it.dipe.opencup.model;

import it.dipe.opencup.model.commond.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D_DMA_DTIN_TIPOLOGIA_INTERVENT")
public class TipologiaIntervento extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4897351414005402717L;
	
	@Id
	@Column(name = "SEQU_DTIN_ID")
	private Integer id;
	 

	@Column(name = "CODI_DTIN_TIPOLOGIA_INTERVENTO", length = 2)
	private String codiTipologiaIntervento;
	 

	@Column(name = "DESC_DTIN_TIPOLOGIA_INTERVENTO", length = 255)
	private String descTipologiaIntervento;
	 

	@ManyToOne(targetEntity = Natura.class)
	@JoinColumn(name = "FK_DTIN_DNAT_ID", referencedColumnName = "SEQU_DNAT_ID")
	private Natura natura;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiTipologiaIntervento() {
		return codiTipologiaIntervento;
	}


	public void setCodiTipologiaIntervento(String codiTipologiaIntervento) {
		this.codiTipologiaIntervento = codiTipologiaIntervento;
	}


	public String getDescTipologiaIntervento() {
		return descTipologiaIntervento;
	}


	public void setDescTipologiaIntervento(String descTipologiaIntervento) {
		this.descTipologiaIntervento = descTipologiaIntervento;
	}


	public Natura getNatura() {
		return natura;
	}


	public void setNatura(Natura natura) {
		this.natura = natura;
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
		TipologiaIntervento other = (TipologiaIntervento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}

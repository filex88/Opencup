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
@Table(name = "D_DMA_DCOM_COMUNE")
public class Comune extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 776549997920722541L;
	
	@Id
	@Column(name = "SEQU_DCOM_ID")
	private Integer id;
	 

	@Column(name = "CODI_DCOM_COMUNE", length = 6)
	private String codiComune;
	 

	@Column(name = "DESC_DCOM_COMUNE", length = 50)
	private String descComune;
	 

	@ManyToOne(targetEntity = Provincia.class)
	@JoinColumn(name = "FK_DCOM_DPRO_ID", referencedColumnName = "SEQU_DPRO_ID")
	private Provincia provincia;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiComune() {
		return codiComune;
	}


	public void setCodiComune(String codiComune) {
		this.codiComune = codiComune;
	}


	public String getDescComune() {
		return descComune;
	}


	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}


	public Provincia getProvincia() {
		return provincia;
	}


	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
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
		Comune other = (Comune) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

}

package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DREG_REGIONE")
public class Regione extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2953137208527252087L;

	@Id
	@Column(name = "SEQU_DREG_ID")
	private Integer id;
	 

	@Column(name = "CODI_DREG_REGIONE", length = 2)
	private String codiRegione;
	 

	@Column(name = "DESC_DREG_REGIONE", length = 50)
	private String descRegione;
	 
	@ManyToOne(targetEntity = AreaGeografica.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DREG_DAGE_ID", referencedColumnName = "SEQU_DAGE_ID")
	private AreaGeografica areaGeografica;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiRegione() {
		return codiRegione;
	}

	public void setCodiRegione(String codiRegione) {
		this.codiRegione = codiRegione;
	}

	public String getDescRegione() {
		return descRegione;
	}

	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}

	public AreaGeografica getAreaGeografica() {
		return areaGeografica;
	}

	public void setAreaGeografica(AreaGeografica areaGeografica) {
		this.areaGeografica = areaGeografica;
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
		Regione other = (Regione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 


}

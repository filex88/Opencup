package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D_DMA_DSPR_STATO_PROGETTO")
public class StatoProgetto extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063654190668822176L;
	
	@Id
	@Column(name = "SEQU_DSPR_ID")
	private Integer id;
	 

	@Column(name = "CODI_DSPR_STATO_PROGETTO", length = 2)
	private String codiStatoProgetto;
	 

	@Column(name = "DESC_DSPR_STATO_PROGETTO", length = 20)
	private String descStatoProgetto;
	 

	@Column(name = "DESC_DSPR_DECOD_STATO_PROGETTO", length = 20)
	private String descDecodStatoProgetto;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiStatoProgetto() {
		return codiStatoProgetto;
	}


	public void setCodiStatoProgetto(String codiStatoProgetto) {
		this.codiStatoProgetto = codiStatoProgetto;
	}


	public String getDescStatoProgetto() {
		return descStatoProgetto;
	}


	public void setDescStatoProgetto(String descStatoProgetto) {
		this.descStatoProgetto = descStatoProgetto;
	}


	public String getDescDecodStatoProgetto() {
		return descDecodStatoProgetto;
	}


	public void setDescDecodStatoProgetto(String descDecodStatoProgetto) {
		this.descDecodStatoProgetto = descDecodStatoProgetto;
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
		StatoProgetto other = (StatoProgetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

}

package it.dipe.opencup.model;

import it.dipe.opencup.model.commond.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D_DMA_DSTP_STRUMENTO_PROGR")
public class StrumentoProgr extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2555392156439661445L;

	@Id
	@Column(name = "SEQU_DSTP_ID")
	private Integer id;
	 

	@Column(name = "CODI_DSTP_STRUMENTO_PROGR", length = 4)
	private String codiStrumentoProgr;
	 

	@Column(name = "DESC_DSTP_STRUMENTO_PROGR", length = 255)
	private String descStrumentoProgr;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiStrumentoProgr() {
		return codiStrumentoProgr;
	}


	public void setCodiStrumentoProgr(String codiStrumentoProgr) {
		this.codiStrumentoProgr = codiStrumentoProgr;
	}


	public String getDescStrumentoProgr() {
		return descStrumentoProgr;
	}


	public void setDescStrumentoProgr(String descStrumentoProgr) {
		this.descStrumentoProgr = descStrumentoProgr;
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
		StrumentoProgr other = (StrumentoProgr) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}

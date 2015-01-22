package it.dipe.opencup.model.commond;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public class AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8160944469195783015L;
	
	@Version
	@Transient
	private Integer versione;

	public Integer getVersione() {
		return versione;
	}

	public void setVersione(Integer versione) {
		this.versione = versione;
	}

	
	
	
}

package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DADE_ANNO_DECISIONE")
public class AnnoDecisione extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2920665192009856745L;

	@Id
	@Column(name = "SEQU_DADE_ID")
	private Integer id;

	@Column(name = "ANNO_DADE_ANNO_DECISIONE", length = 4)
	private String annoDadeAnnoDecisione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnnoDadeAnnoDecisione() {
		return annoDadeAnnoDecisione;
	}

	public void setAnnoDadeAnnoDecisione(String annoDadeAnnoDecisione) {
		this.annoDadeAnnoDecisione = annoDadeAnnoDecisione;
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
		AnnoDecisione other = (AnnoDecisione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	
}

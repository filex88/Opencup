package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DSEA_SEZIONE_ATECO")
public class SezioneAteco extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1707633445034955554L;
	
	@Id
	@Column(name = "SEQU_DSEA_ID")
	private Integer id;

	@Column(name = "CODI_DSEA_SEZIONE_ATECO", length = 1)
	private String codiSezioneAteco;

	@Column(name = "DESC_DSEA_SEZIONE_ATECO", length = 255)
	private String descSezioneAteco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiSezioneAteco() {
		return codiSezioneAteco;
	}

	public void setCodiSezioneAteco(String codiSezioneAteco) {
		this.codiSezioneAteco = codiSezioneAteco;
	}

	public String getDescSezioneAteco() {
		return descSezioneAteco;
	}

	public void setDescSezioneAteco(String descSezioneAteco) {
		this.descSezioneAteco = descSezioneAteco;
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
		SezioneAteco other = (SezioneAteco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	


}

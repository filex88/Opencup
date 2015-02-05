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
@Table(name = "S_DMA_DSSA_SOTTOSEZIONE_ATECO")
public class SottosezioneAteco extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2406408007298404750L;

	@Id
	@Column(name = "SEQU_DSSA_ID")
	private Integer id;

	@Column(name = "CODI_DSSA_SOTTOSEZIONE_ATECO", length = 2)
	private String codiSottosezioneAteco;

	@Column(name = "DESC_DSSA_SOTTOSEZIONE_ATECO", length = 255)
	private String descSottosezioneAteco;

	@ManyToOne(targetEntity = SezioneAteco.class)
	@JoinColumn(name = "FK_DSSA_DSEA_ID", referencedColumnName = "SEQU_DSEA_ID")
	private SezioneAteco sezioneAteco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiSottosezioneAteco() {
		return codiSottosezioneAteco;
	}

	public void setCodiSottosezioneAteco(String codiSottosezioneAteco) {
		this.codiSottosezioneAteco = codiSottosezioneAteco;
	}

	public String getDescSottosezioneAteco() {
		return descSottosezioneAteco;
	}

	public void setDescSottosezioneAteco(String descSottosezioneAteco) {
		this.descSottosezioneAteco = descSottosezioneAteco;
	}

	public SezioneAteco getSezioneAteco() {
		return sezioneAteco;
	}

	public void setSezioneAteco(SezioneAteco sezioneAteco) {
		this.sezioneAteco = sezioneAteco;
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
		SottosezioneAteco other = (SottosezioneAteco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

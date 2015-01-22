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
@Table(name = "D_DMA_DDIA_DIVISIONE_ATECO")
public class DivisioneAteco extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8920544085145978616L;
	
	@Id
	@Column(name = "SEQU_DDIA_ID")
	private Integer id;

	@Column(name = "CODI_DDIA_DIVISIONE_ATECO", length = 2)
	private String codiDivisioneAteco;

	@Column(name = "DESC_DDIA_DIVISIONE_ATECO", length = 255)
	private String descDivisioneAteco;

	@ManyToOne(targetEntity = SottosezioneAteco.class)
	@JoinColumn(name = "FK_DDIA_DSSA_ID", referencedColumnName = "SEQU_DSSA_ID")
	private SottosezioneAteco sottosezioneAteco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiDivisioneAteco() {
		return codiDivisioneAteco;
	}

	public void setCodiDivisioneAteco(String codiDivisioneAteco) {
		this.codiDivisioneAteco = codiDivisioneAteco;
	}

	public String getDescDivisioneAteco() {
		return descDivisioneAteco;
	}

	public void setDescDivisioneAteco(String descDivisioneAteco) {
		this.descDivisioneAteco = descDivisioneAteco;
	}

	public SottosezioneAteco getSottosezioneAteco() {
		return sottosezioneAteco;
	}

	public void setSottosezioneAteco(SottosezioneAteco sottosezioneAteco) {
		this.sottosezioneAteco = sottosezioneAteco;
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
		DivisioneAteco other = (DivisioneAteco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}

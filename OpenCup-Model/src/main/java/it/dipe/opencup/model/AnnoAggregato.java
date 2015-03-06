package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DAAG_ANNO_AGGREGATO")
public class AnnoAggregato extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5744164534669305636L;

	@Id
	@Column(name = "SEQU_DAAG_ID")
	private Integer id;

	@Column(name = "DESC_DAGG_ANNO_AGGREGATO", length = 4)
	private String annoAggregato;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnnoAggregato() {
		return annoAggregato;
	}

	public void setAnnoAggregato(String annoAggregato) {
		this.annoAggregato = annoAggregato;
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
		AnnoAggregato other = (AnnoAggregato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}

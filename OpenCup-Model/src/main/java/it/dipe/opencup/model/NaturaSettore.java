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
@Table(name = "S_DMA_DNAS_NATURA_SETTORE")
public class NaturaSettore extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8823580166849148334L;
	
	@Id
	@Column(name = "SEQU_DNAS_ID")
	private Integer id;
	
	
	@ManyToOne(targetEntity = Natura.class)
	@JoinColumn(name = "FK_DNAS_DNAT_ID", referencedColumnName = "SEQU_DNAT_ID")
	private Natura natura;
	 
	@ManyToOne(targetEntity = SettoreIntervento.class)
	@JoinColumn(name = "FK_DNAS_DSIN_ID", referencedColumnName = "SEQU_DSIN_ID")
	private SettoreIntervento settoreIntervento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Natura getNatura() {
		return natura;
	}

	public void setNatura(Natura natura) {
		this.natura = natura;
	}

	public SettoreIntervento getSettoreIntervento() {
		return settoreIntervento;
	}

	public void setSettoreIntervento(SettoreIntervento settoreIntervento) {
		this.settoreIntervento = settoreIntervento;
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
		NaturaSettore other = (NaturaSettore) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

}

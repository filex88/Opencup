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
@Table(name = "D_DMA_DSSI_SOTTOSETTORE_INTERV")
public class SottosettoreIntervento extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5756482141716364608L;
	
	@Id
	@Column(name = "SEQU_DSSI_ID")
	private Integer id;
	 

	@Column(name = "CODI_DSSI_SOTTOSETTORE_INT", length = 4)
	private String codiSottosettoreInt;
	 

	@Column(name = "DESC_DSSI_SOTTOSETTORE_INT", length = 255)
	private String descSottosettoreInt;
	 
	@ManyToOne(targetEntity = SettoreIntervento.class)
	@JoinColumn(name = "FK_DSSI_DSIN_ID", referencedColumnName = "SEQU_DSIN_ID")
	private SettoreIntervento settoreIntervento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiSottosettoreInt() {
		return codiSottosettoreInt;
	}

	public void setCodiSottosettoreInt(String codiSottosettoreInt) {
		this.codiSottosettoreInt = codiSottosettoreInt;
	}

	public String getDescSottosettoreInt() {
		return descSottosettoreInt;
	}

	public void setDescSottosettoreInt(String descSottosettoreInt) {
		this.descSottosettoreInt = descSottosettoreInt;
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
		SottosettoreIntervento other = (SottosettoreIntervento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 


}

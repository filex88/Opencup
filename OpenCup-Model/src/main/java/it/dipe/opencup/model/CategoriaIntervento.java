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
@Table(name = "S_DMA_DCAI_CATEGORIA_INTERVENT")
public class CategoriaIntervento extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4440332907007992494L;
	

	@Id
	@Column(name = "SEQU_DCAI_ID")
	private Integer id;
	 

	@Column(name = "CODI_DCAI_CATEGORIA_INTERVENTO", length = 4)
	private String codiCategoriaIntervento;
	 

	@Column(name = "DESC_DCAI_CATEGORIA_INTERVENTO", length = 255)
	private String descCategoriaIntervento;
	 
	@ManyToOne(targetEntity = SettoreIntervento.class)
	@JoinColumn(name = "FK_DCAI_DSIN_ID", referencedColumnName = "SEQU_DSIN_ID")
	private SettoreIntervento settoreIntervento;
	
	@ManyToOne(targetEntity = SottosettoreIntervento.class)
	@JoinColumn(name = "FK_DCAI_DSSI_ID", referencedColumnName = "SEQU_DSSI_ID")
	private SottosettoreIntervento sottosettoreIntervento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiCategoriaIntervento() {
		return codiCategoriaIntervento;
	}

	public void setCodiCategoriaIntervento(String codiCategoriaIntervento) {
		this.codiCategoriaIntervento = codiCategoriaIntervento;
	}

	public String getDescCategoriaIntervento() {
		return descCategoriaIntervento;
	}

	public void setDescCategoriaIntervento(String descCategoriaIntervento) {
		this.descCategoriaIntervento = descCategoriaIntervento;
	}

	public SettoreIntervento getSettoreIntervento() {
		return settoreIntervento;
	}

	public void setSettoreIntervento(SettoreIntervento settoreIntervento) {
		this.settoreIntervento = settoreIntervento;
	}

	public SottosettoreIntervento getSottosettoreIntervento() {
		return sottosettoreIntervento;
	}

	public void setSottosettoreIntervento(
			SottosettoreIntervento sottosettoreIntervento) {
		this.sottosettoreIntervento = sottosettoreIntervento;
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
		CategoriaIntervento other = (CategoriaIntervento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}

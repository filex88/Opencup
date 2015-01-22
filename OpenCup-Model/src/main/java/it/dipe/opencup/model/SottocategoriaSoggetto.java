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
@Table(name = "D_DMA_DSCS_SOTTOCATEG_SOGGETTO")
public class SottocategoriaSoggetto extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2662772083395689264L;

	@Id
	@Column(name = "SEQU_DSCS_ID")
	private Integer id;
	 

	@Column(name = "CODI_DSCS_SOTTOCATEG_SOGGETTO", length = 4)
	private String codiSottocategSoggetto;
	

	@Column(name = "DESC_DSCS_SOTTOCATEG_SOGGETTO", length = 255)
	private String descSottocategSoggetto;
	 

	@ManyToOne(targetEntity = CategoriaSoggetto.class)
	@JoinColumn(name = "FK_DSCS_ECSO_ID", referencedColumnName = "SEQU_DCSO_ID")
	private CategoriaSoggetto categoriaSoggetto;
	


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiSottocategSoggetto() {
		return codiSottocategSoggetto;
	}


	public void setCodiSottocategSoggetto(String codiSottocategSoggetto) {
		this.codiSottocategSoggetto = codiSottocategSoggetto;
	}


	public String getDescSottocategSoggetto() {
		return descSottocategSoggetto;
	}


	public void setDescSottocategSoggetto(String descSottocategSoggetto) {
		this.descSottocategSoggetto = descSottocategSoggetto;
	}


	public CategoriaSoggetto getCategoriaSoggetto() {
		return categoriaSoggetto;
	}


	public void setCategoriaSoggetto(CategoriaSoggetto categoriaSoggetto) {
		this.categoriaSoggetto = categoriaSoggetto;
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
		SottocategoriaSoggetto other = (SottocategoriaSoggetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

}

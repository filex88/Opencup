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
@Table(name = "S_DMA_DGSO_GERARCHIA_SOGGETTO")
public class GerarchiaSoggetto extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2458938368930212936L;
	
	@Id
	@Column(name = "SEQU_DGSO_ID")
	private Integer id;
				
	@Column(name = "DESC_DGSO_CATEGORIA", length = 150)
	private String descCategSoggetto;
	
	@Column(name = "DESC_DGSO_SOTTOCATEGORIA", length = 255)
	private String descSottocategSoggetto;
				
	@ManyToOne(targetEntity = CategoriaSoggetto.class)
	@JoinColumn(name = "FK_DGSO_DCSO_ID", referencedColumnName = "SEQU_DCSO_ID")
	private CategoriaSoggetto categoriaSoggetto;
	
	@ManyToOne(targetEntity = SottocategoriaSoggetto.class)
	@JoinColumn(name = "FK_DGSO_DSCS_ID", referencedColumnName = "SEQU_DSCS_ID")
	private SottocategoriaSoggetto sottocategoriaSoggetto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescCategSoggetto() {
		return descCategSoggetto;
	}

	public void setDescCategSoggetto(String descCategSoggetto) {
		this.descCategSoggetto = descCategSoggetto;
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

	public SottocategoriaSoggetto getSottocategoriaSoggetto() {
		return sottocategoriaSoggetto;
	}

	public void setSottocategoriaSoggetto(
			SottocategoriaSoggetto sottocategoriaSoggetto) {
		this.sottocategoriaSoggetto = sottocategoriaSoggetto;
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
		GerarchiaSoggetto other = (GerarchiaSoggetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}

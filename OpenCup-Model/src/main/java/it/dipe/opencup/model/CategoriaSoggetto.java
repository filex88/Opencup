package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DCSO_CATEGORIA_SOGGETTO")
public class CategoriaSoggetto extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6305209138186606963L;
	
	@Id
	@Column(name = "SEQU_DCSO_ID")
	private Integer id;

	@Column(name = "CODI_DCSO_CATEGORIA_SOGGETTO", length = 2, columnDefinition = "char")
	private String codiCategoriaSoggetto;

	@Column(name = "DESC_DCSO_CATEGORIA_SOGGETTO", length = 150, columnDefinition = "char")
	private String descCategoriaSoggetto;
	
//	@ManyToOne(targetEntity = AreaSoggetto.class)
//	@JoinColumn(name = "FK_DCSO_DASO_ID", referencedColumnName = "SEQU_DASO_ID")
//	private AreaSoggetto areaSoggetto;


//	public AreaSoggetto getAreaSoggetto() {
//		return areaSoggetto;
//	}
//
//
//	public void setAreaSoggetto(AreaSoggetto areaSoggetto) {
//		this.areaSoggetto = areaSoggetto;
//	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiCategoriaSoggetto() {
		return codiCategoriaSoggetto;
	}


	public void setCodiCategoriaSoggetto(String codiCategoriaSoggetto) {
		this.codiCategoriaSoggetto = codiCategoriaSoggetto;
	}


	public String getDescCategoriaSoggetto() {
		return descCategoriaSoggetto;
	}


	public void setDescCategoriaSoggetto(String descCategoriaSoggetto) {
		this.descCategoriaSoggetto = descCategoriaSoggetto;
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
		CategoriaSoggetto other = (CategoriaSoggetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 
	

}

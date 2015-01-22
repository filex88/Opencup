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
@Table(name = "D_DMA_DGRA_GRUPPO_ATECO")
public class GruppoAteco extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1258634711044328138L;

	
	@Id
	@Column(name = "SEQU_DGRA_ID")
	private Integer id;

	@Column(name = "CODI_DGRA_GRUPPO_ATECO", length = 7)
	private String codiGruppoAteco;

	@Column(name = "DESC_DGRA_GRUPPO_ATECO", length = 255)
	private String descGruppoAteco;

	@ManyToOne(targetEntity = DivisioneAteco.class)
	@JoinColumn(name = "FK_DGRA_DDIA_ID", referencedColumnName = "SEQU_DDIA_ID")
	private DivisioneAteco divisioneAteco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiGruppoAteco() {
		return codiGruppoAteco;
	}

	public void setCodiGruppoAteco(String codiGruppoAteco) {
		this.codiGruppoAteco = codiGruppoAteco;
	}

	public String getDescGruppoAteco() {
		return descGruppoAteco;
	}

	public void setDescGruppoAteco(String descGruppoAteco) {
		this.descGruppoAteco = descGruppoAteco;
	}

	public DivisioneAteco getDivisioneAteco() {
		return divisioneAteco;
	}

	public void setDivisioneAteco(DivisioneAteco divisioneAteco) {
		this.divisioneAteco = divisioneAteco;
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
		GruppoAteco other = (GruppoAteco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}

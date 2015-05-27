package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_FPRT_PROGETTI_TOTALI")
public class ProgettiTotali extends AbstractCommonEntity implements
		Serializable {

	private static final long serialVersionUID = -973924819056428799L;

	@Id
	@Column(name = "SEQU_FPRT_ID")
	private Integer id;

	// @ManyToOne(targetEntity = Natura.class)
	// @JoinColumn(name = "FK_FPRT_DNAT_ID", referencedColumnName =
	// "SEQU_DNAT_ID")
	// private Natura natura;

	@Column(name = "FK_FPRT_DNAT_ID")
	private Integer idNatura;

	@Column(name = "NUME_FPRT_PROGETTI", columnDefinition = "NUMBER", precision = 12, scale = 0)
	private Double numeProgetti;

	@Column(name = "IMPO_FPRT_COSTO_PROGETTI", columnDefinition = "NUMBER", precision = 18, scale = 3)
	private Double impoCostoProgetti;

	@Column(name = "IMPO_FPRT_IMPORTO_FINANZIATO", columnDefinition = "NUMBER", precision = 18, scale = 3)
	private Double impoImportoFinanziato;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getNumeProgetti() {
		return numeProgetti;
	}

	public void setNumeProgetti(Double numeProgetti) {
		this.numeProgetti = numeProgetti;
	}

	public Double getImpoCostoProgetti() {
		return impoCostoProgetti;
	}

	public void setImpoCostoProgetti(Double impoCostoProgetti) {
		this.impoCostoProgetti = impoCostoProgetti;
	}

	public Double getImpoImportoFinanziato() {
		return impoImportoFinanziato;
	}

	public void setImpoImportoFinanziato(Double impoImportoFinanziato) {
		this.impoImportoFinanziato = impoImportoFinanziato;
	}

	public Integer getIdNatura() {
		return idNatura;
	}

	public void setIdNatura(Integer idNatura) {
		this.idNatura = idNatura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idNatura == null) ? 0 : idNatura.hashCode());
		result = prime
				* result
				+ ((impoCostoProgetti == null) ? 0 : impoCostoProgetti
						.hashCode());
		result = prime
				* result
				+ ((impoImportoFinanziato == null) ? 0 : impoImportoFinanziato
						.hashCode());
		result = prime * result
				+ ((numeProgetti == null) ? 0 : numeProgetti.hashCode());
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
		ProgettiTotali other = (ProgettiTotali) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNatura == null) {
			if (other.idNatura != null)
				return false;
		} else if (!idNatura.equals(other.idNatura))
			return false;
		if (impoCostoProgetti == null) {
			if (other.impoCostoProgetti != null)
				return false;
		} else if (!impoCostoProgetti.equals(other.impoCostoProgetti))
			return false;
		if (impoImportoFinanziato == null) {
			if (other.impoImportoFinanziato != null)
				return false;
		} else if (!impoImportoFinanziato.equals(other.impoImportoFinanziato))
			return false;
		if (numeProgetti == null) {
			if (other.numeProgetti != null)
				return false;
		} else if (!numeProgetti.equals(other.numeProgetti))
			return false;
		return true;
	}

}

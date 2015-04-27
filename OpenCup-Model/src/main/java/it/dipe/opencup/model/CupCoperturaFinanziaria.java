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
@Table(name = "S_DMA_ACCF_CUP_COPER_FIN")
public class CupCoperturaFinanziaria extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -333315391548738968L;
	
	@Id
	@Column(name = "SEQU_ACCF_ID")
	private Integer id;
	
	@ManyToOne(targetEntity = AnagraficaCup.class)
	@JoinColumn(name = "FK_ACCF_DCUP_ID", referencedColumnName = "SEQU_DCUP_ID")
	private AnagraficaCup anagraficaCup;
	
	@ManyToOne(targetEntity = TipoCopertura.class)
	@JoinColumn(name = "FK_ACCF_DTCF_ID", referencedColumnName = "SEQU_DTCF_ID")
	private TipoCopertura tipoCopertura;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AnagraficaCup getAnagraficaCup() {
		return anagraficaCup;
	}

	public void setAnagraficaCup(AnagraficaCup anagraficaCup) {
		this.anagraficaCup = anagraficaCup;
	}

	public TipoCopertura getTipoCopertura() {
		return tipoCopertura;
	}

	public void setTipoCopertura(TipoCopertura tipoCopertura) {
		this.tipoCopertura = tipoCopertura;
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
		CupCoperturaFinanziaria other = (CupCoperturaFinanziaria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

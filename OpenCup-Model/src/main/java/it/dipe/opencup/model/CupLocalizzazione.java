package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_ACLO_CUP_LOCALIZZAZIONE")
public class CupLocalizzazione extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5525243631650682031L;

	@Id
	@Column(name = "SEQU_ACLO_ID")
	private Integer id;

	
	@ManyToOne(targetEntity = Comune.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACLO_DCOM_ID", referencedColumnName = "SEQU_DCOM_ID")
	private Comune comune;
		 
	
	@ManyToOne(targetEntity = Provincia.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACLO_DPRO_ID", referencedColumnName = "SEQU_DPRO_ID")
	private Provincia provincia;
	 

	@ManyToOne(targetEntity = Regione.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACLO_DREG_ID", referencedColumnName = "SEQU_DREG_ID")
	private Regione regione;
	 

	@ManyToOne(targetEntity = Stato.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACLO_DSTA_ID", referencedColumnName = "SEQU_DSTA_ID")
	private Stato stato;
	 

	@ManyToOne(targetEntity = AreaGeografica.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACLO_DAGE_ID", referencedColumnName = "SEQU_DAGE_ID")
	private AreaGeografica areaGeografica;
	
	@ManyToOne(targetEntity = AnagraficaCup.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACLO_DCUP_ID", referencedColumnName = "SEQU_DCUP_ID")
	private AnagraficaCup anagraficaCup;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Comune getComune() {
		return comune;
	}


	public void setComune(Comune comune) {
		this.comune = comune;
	}


	public Provincia getProvincia() {
		return provincia;
	}


	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}


	public Regione getRegione() {
		return regione;
	}


	public void setRegione(Regione regione) {
		this.regione = regione;
	}


	public Stato getStato() {
		return stato;
	}


	public void setStato(Stato stato) {
		this.stato = stato;
	}


	public AreaGeografica getAreaGeografica() {
		return areaGeografica;
	}


	public void setAreaGeografica(AreaGeografica areaGeografica) {
		this.areaGeografica = areaGeografica;
	}

	
	public AnagraficaCup getAnagraficaCup() {
		return anagraficaCup;
	}


	public void setAnagraficaCup(AnagraficaCup anagraficaCup) {
		this.anagraficaCup = anagraficaCup;
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
		CupLocalizzazione other = (CupLocalizzazione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 


}

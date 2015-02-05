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
@Table(name = "S_DMA_DLOC_LOCALIZZAZIONE")
public class Localizzazione extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4722000888867117432L;
	
	@Id
	@Column(name = "SEQU_DLOC_ID")
	private Integer id;
	 

	@Column(name = "DESC_DLOC_PROVINCIA", length = 55)
	private String descProvincia;
	 

	@Column(name = "DESC_DLOC_REGIONE", length = 50)
	private String descRegione;
	 

	@Column(name = "DESC_DLOC_STATO", length = 50)
	private String descStato;
	
	@Column(name = "DESC_DLOC_AREA_GEOGRAFICA", length = 50)
	private String descAreaGeografica;
	 
	
	@ManyToOne(targetEntity = Provincia.class)
	@JoinColumn(name = "FK_DLOC_DPRO_ID", referencedColumnName = "SEQU_DPRO_ID")
	private Provincia provincia;
	 

	@ManyToOne(targetEntity = Regione.class)
	@JoinColumn(name = "FK_DLOC_DREG_ID", referencedColumnName = "SEQU_DREG_ID")
	private Regione regione;
	 

	@ManyToOne(targetEntity = Stato.class)
	@JoinColumn(name = "FK_DLOC_DSTA_ID", referencedColumnName = "SEQU_DSTA_ID")
	private Stato stato;
	 

	@ManyToOne(targetEntity = AreaGeografica.class)
	@JoinColumn(name = "FK_DLOC_DAGE_ID", referencedColumnName = "SEQU_DAGE_ID")
	private AreaGeografica areaGeografica;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescProvincia() {
		return descProvincia;
	}


	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}


	public String getDescRegione() {
		return descRegione;
	}


	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}


	public String getDescStato() {
		return descStato;
	}


	public void setDescStato(String descStato) {
		this.descStato = descStato;
	}


	public String getDescAreaGeografica() {
		return descAreaGeografica;
	}


	public void setDescAreaGeografica(String descAreaGeografica) {
		this.descAreaGeografica = descAreaGeografica;
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
		Localizzazione other = (Localizzazione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

	

	 


}

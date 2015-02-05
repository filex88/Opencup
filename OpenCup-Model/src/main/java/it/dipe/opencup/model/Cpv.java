package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_DMA_DCPV_CPV")
public class Cpv extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7423332221471118721L;
	
	@Id
	@Column(name = "SEQU_DCPV_ID")
	private Integer id;
	 

	@Column(name = "CODI_DCPV_I_LIVELLO", length = 10)
	private String codiILivello;
	 

	@Column(name = "CODI_DCPV_II_LIVELLO", length = 10)
	private String codiIILivello;
	 

	@Column(name = "CODI_DCPV_III_LIVELLO", length = 10)
	private String codiIIILivello;
	 

	@Column(name = "CODI_DCPV_IV_LIVELLO", length = 10)
	private String codiIVLivello;
	 

	@Column(name = "CODI_DCPV_V_LIVELLO", length = 10)
	private String codiVLivello;
	 

	@Column(name = "CODI_DCPV_VI_LIVELLO", length = 10)
	private String codiVILivello;
	 

	@Column(name = "CODI_DCPV_VII_LIVELLO", length = 10)
	private String codiVIILivello;
	 

	@Column(name = "DESC_DCPV_I_LIVELLO", length = 255)
	private String descILivello;
	 

	@Column(name = "DESC_DCPV_II_LIVELLO", length = 255)
	private String descIILivello;
	 

	@Column(name = "DESC_DCPV_III_LIVELLO", length = 255)
	private String descIIILivello;
	 

	@Column(name = "DESC_DCPV_IV_LIVELLO", length = 255)
	private String descIVLivello;
	 

	@Column(name = "DESC_DCPV_V_LIVELLO", length = 255)
	private String descVLivello;
	 

	@Column(name = "DESC_DCPV_VI_LIVELLO", length = 255)
	private String descVILivello;
	 

	@Column(name = "DESC_DCPV_VII_LIVELLO", length = 255)
	private String descVIILivello;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiILivello() {
		return codiILivello;
	}


	public void setCodiILivello(String codiILivello) {
		this.codiILivello = codiILivello;
	}


	public String getCodiIILivello() {
		return codiIILivello;
	}


	public void setCodiIILivello(String codiIILivello) {
		this.codiIILivello = codiIILivello;
	}


	public String getCodiIIILivello() {
		return codiIIILivello;
	}


	public void setCodiIIILivello(String codiIIILivello) {
		this.codiIIILivello = codiIIILivello;
	}


	public String getCodiIVLivello() {
		return codiIVLivello;
	}


	public void setCodiIVLivello(String codiIVLivello) {
		this.codiIVLivello = codiIVLivello;
	}


	public String getCodiVLivello() {
		return codiVLivello;
	}


	public void setCodiVLivello(String codiVLivello) {
		this.codiVLivello = codiVLivello;
	}


	public String getCodiVILivello() {
		return codiVILivello;
	}


	public void setCodiVILivello(String codiVILivello) {
		this.codiVILivello = codiVILivello;
	}


	public String getCodiVIILivello() {
		return codiVIILivello;
	}


	public void setCodiVIILivello(String codiVIILivello) {
		this.codiVIILivello = codiVIILivello;
	}


	public String getDescILivello() {
		return descILivello;
	}


	public void setDescILivello(String descILivello) {
		this.descILivello = descILivello;
	}


	public String getDescIILivello() {
		return descIILivello;
	}


	public void setDescIILivello(String descIILivello) {
		this.descIILivello = descIILivello;
	}


	public String getDescIIILivello() {
		return descIIILivello;
	}


	public void setDescIIILivello(String descIIILivello) {
		this.descIIILivello = descIIILivello;
	}


	public String getDescIVLivello() {
		return descIVLivello;
	}


	public void setDescIVLivello(String descIVLivello) {
		this.descIVLivello = descIVLivello;
	}


	public String getDescVLivello() {
		return descVLivello;
	}


	public void setDescVLivello(String descVLivello) {
		this.descVLivello = descVLivello;
	}


	public String getDescVILivello() {
		return descVILivello;
	}


	public void setDescVILivello(String descVILivello) {
		this.descVILivello = descVILivello;
	}


	public String getDescVIILivello() {
		return descVIILivello;
	}


	public void setDescVIILivello(String descVIILivello) {
		this.descVIILivello = descVIILivello;
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
		Cpv other = (Cpv) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 


}

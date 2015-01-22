package it.dipe.opencup.model;

import it.dipe.opencup.model.commond.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D_DMA_DNAT_NATURA")
public class Natura extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -218184423333037801L;
	
	
	@Id
	@Column(name = "SEQU_DNAT_ID")
	private Integer id;
	 

	@Column(name = "CODI_DNAT_NATURA", length = 2)
	private String codiNatura;
	 

	@Column(name = "DESC_DNAT_NATURA", length = 100)
	private String descNatura;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiNatura() {
		return codiNatura;
	}


	public void setCodiNatura(String codiNatura) {
		this.codiNatura = codiNatura;
	}


	public String getDescNatura() {
		return descNatura;
	}


	public void setDescNatura(String descNatura) {
		this.descNatura = descNatura;
	}
	 

}

package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "S_DMA_DCLA_CLASSIFICAZIONE")
public class Classificazione extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6806888184216159792L;

	@Id
	@Column(name = "SEQU_DCLA_ID")
	private Integer id;
	 

	@Column(name = "DESC_DCLA_NATURA", length = 100)
	private String descNatura;
	 

	@Column(name = "DESC_DCLA_AREA_INTERVENTO", length = 255)
	private String descAreaIntervento;
	 

	@Column(name = "DESC_DCLA_SOTTOSETTORE", length = 255)
	private String descSottosettore;
	 

	@Column(name = "DESC_DCLA_CATEGORIA", length = 255)
	private String descCategoria;
	 
	@ManyToOne(targetEntity = Natura.class)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "FK_DCLA_DNAT_ID", referencedColumnName = "SEQU_DNAT_ID")
	private Natura natura;
	 

	@ManyToOne(targetEntity = AreaIntervento.class)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "FK_DCLA_DAIN_ID", referencedColumnName = "SEQU_DAIN_ID")
	private AreaIntervento areaIntervento;

	
	@ManyToOne(targetEntity = SottosettoreIntervento.class)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "FK_DCLA_DSSI_ID", referencedColumnName = "SEQU_DSSI_ID")
	private SottosettoreIntervento sottosettoreIntervento;
	 
	
	@ManyToOne(targetEntity = CategoriaIntervento.class)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "FK_DCAL_DCAI_ID", referencedColumnName = "SEQU_DCAI_ID")
	private CategoriaIntervento categoriaIntervento;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescNatura() {
		return descNatura;
	}


	public void setDescNatura(String descNatura) {
		this.descNatura = descNatura;
	}


	public String getDescAreaIntervento() {
		return descAreaIntervento;
	}


	public void setDescAreaIntervento(String descAreaIntervento) {
		this.descAreaIntervento = descAreaIntervento;
	}


	public String getDescSottosettore() {
		return descSottosettore;
	}


	public void setDescSottosettore(String descSottosettore) {
		this.descSottosettore = descSottosettore;
	}


	public String getDescCategoria() {
		return descCategoria;
	}


	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}


	public Natura getNatura() {
		return natura;
	}


	public void setNatura(Natura natura) {
		this.natura = natura;
	}


	public AreaIntervento getAreaIntervento() {
		return areaIntervento;
	}


	public void setAreaIntervento(AreaIntervento areaIntervento) {
		this.areaIntervento = areaIntervento;
	}


	public SottosettoreIntervento getSottosettoreIntervento() {
		return sottosettoreIntervento;
	}


	public void setSottosettoreIntervento(
			SottosettoreIntervento sottosettoreIntervento) {
		this.sottosettoreIntervento = sottosettoreIntervento;
	}


	public CategoriaIntervento getCategoriaIntervento() {
		return categoriaIntervento;
	}


	public void setCategoriaIntervento(CategoriaIntervento categoriaIntervento) {
		this.categoriaIntervento = categoriaIntervento;
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
		Classificazione other = (Classificazione) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

	

}

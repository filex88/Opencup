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
@Table(name = "S_DMA_DUOR_UNITA_ORGANIZZATIVA")
public class UnitaOrganizzativa extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1460990539913238211L;


	@Id
	@Column(name = "SEQU_DUOR_ID")
	private Integer id;
	 

	@Column(name = "CODI_DUOR_UNITA_ORGANIZZATIVA", length = 4)
	private String codiUnitaOrganizzativa;
	 

	@Column(name = "DESC_DUOR_UNITA_ORGANIZZATIVA", length = 255)
	private String descUnitaOrganizzativa;
	 

	@Column(name = "CODI_DUOR_CODFISCALE_PIVA", length = 16)
	private String codiCodfiscalePiva;
	 

	@Column(name = "TEXT_DUOR_TIPO_INDIRIZZO", length = 20)
	private String textTipoIndirizzo;
	 

	@Column(name = "TEXT_DUOR_INDIRIZZO", length = 100)
	private String textIndirizzo;
	 

	@Column(name = "NUME_DUOR_NUMERO_CIVICO", length = 10)
	private String numeNumeroCivico;
	 

	@Column(name = "TEXT_DUOR_LOCALITA", length = 50)
	private String textLocalita;
	 

	@Column(name = "TEXT_DUOR_CAP", length = 5)
	private String textCap;
	 

	@Column(name = "TEXT_DUOR_EMAIL_ISTITUZIONALE", length = 100)
	private String textEmailIstituzionale;
	 

	@ManyToOne(targetEntity = SoggettoTitolare.class)
	@JoinColumn(name = "FK_DUOR_DSOG_ID", referencedColumnName = "SEQU_DSOG_ID")
	private SoggettoTitolare soggettoTitolare;
	 

	@ManyToOne(targetEntity = Comune.class)
	@JoinColumn(name = "FK_DUOR_DCOM_ID", referencedColumnName = "SEQU_DCOM_ID")
	private Comune comune;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiUnitaOrganizzativa() {
		return codiUnitaOrganizzativa;
	}


	public void setCodiUnitaOrganizzativa(String codiUnitaOrganizzativa) {
		this.codiUnitaOrganizzativa = codiUnitaOrganizzativa;
	}


	public String getDescUnitaOrganizzativa() {
		return descUnitaOrganizzativa;
	}


	public void setDescUnitaOrganizzativa(String descUnitaOrganizzativa) {
		this.descUnitaOrganizzativa = descUnitaOrganizzativa;
	}


	public String getCodiCodfiscalePiva() {
		return codiCodfiscalePiva;
	}


	public void setCodiCodfiscalePiva(String codiCodfiscalePiva) {
		this.codiCodfiscalePiva = codiCodfiscalePiva;
	}


	public String getTextTipoIndirizzo() {
		return textTipoIndirizzo;
	}


	public void setTextTipoIndirizzo(String textTipoIndirizzo) {
		this.textTipoIndirizzo = textTipoIndirizzo;
	}


	public String getTextIndirizzo() {
		return textIndirizzo;
	}


	public void setTextIndirizzo(String textIndirizzo) {
		this.textIndirizzo = textIndirizzo;
	}


	public String getNumeNumeroCivico() {
		return numeNumeroCivico;
	}


	public void setNumeNumeroCivico(String numeNumeroCivico) {
		this.numeNumeroCivico = numeNumeroCivico;
	}


	public String getTextLocalita() {
		return textLocalita;
	}


	public void setTextLocalita(String textLocalita) {
		this.textLocalita = textLocalita;
	}


	public String getTextCap() {
		return textCap;
	}


	public void setTextCap(String textCap) {
		this.textCap = textCap;
	}


	public String getTextEmailIstituzionale() {
		return textEmailIstituzionale;
	}


	public void setTextEmailIstituzionale(String textEmailIstituzionale) {
		this.textEmailIstituzionale = textEmailIstituzionale;
	}


	public SoggettoTitolare getSoggettoTitolare() {
		return soggettoTitolare;
	}


	public void setSoggettoTitolare(SoggettoTitolare soggettoTitolare) {
		this.soggettoTitolare = soggettoTitolare;
	}


	public Comune getComune() {
		return comune;
	}


	public void setComune(Comune comune) {
		this.comune = comune;
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
		UnitaOrganizzativa other = (UnitaOrganizzativa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

	
}

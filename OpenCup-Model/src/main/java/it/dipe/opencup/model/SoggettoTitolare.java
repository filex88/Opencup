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
@Table(name = "S_DMA_DSOG_SOGGETTO_TITOLARE")
public class SoggettoTitolare extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -812268777625091930L;

	@Id
	@Column(name = "SEQU_DSOG_ID")
	private Integer id;
	 

	@Column(name = "CODI_DSOG_SOGGETTO_TITOLARE", length = 6)
	private String codiSoggettoTitolare;
	 

	@Column(name = "DESC_DSOG_SOGGETTO_TITOLARE", length = 255)
	private String descSoggettoTitolare;
	 

	@Column(name = "CODI_DSOG_CODFISCALE_PIVA", length = 16)
	private String codiCodfiscalePiva;
	 

	@Column(name = "TEXT_DSOG_INDIRIZZO", length = 100)
	private String textIndirizzo;
	 

	@Column(name = "TEXT_DSOG_TIPO_INDIRIZZO", length = 20)
	private String textTipoIndirizzo;
	 

	@Column(name = "NUME_DSOG_NUMERO_CIVICO", length = 10)
	private String numeNumeroCivico;
	 

	@Column(name = "TEXT_DSOG_LOCALITA", length = 50)
	private String textLocalita;
	 

	@Column(name = "TEXT_DSOG_CAP", length = 5)
	private String textCap;
	 

	@Column(name = "FLAG_DSOG_CONCENTRATORE", length = 1)
	private String flagConcentratore;
	 

	@Column(name = "CODI_DSOG_ESTERNO", length = 20)
	private String codiEsterno;
	 
	
	@ManyToOne(targetEntity = CategoriaSoggetto.class)
	@JoinColumn(name = "FK_DSOG_DCSO_ID", referencedColumnName = "SEQU_DCSO_ID")
	private CategoriaSoggetto categoriaSoggetto;
	 

	@ManyToOne(targetEntity = SottocategoriaSoggetto.class)
	@JoinColumn(name = "FK_DSOG_DSCS_ID", referencedColumnName = "SEQU_DSCS_ID")
	private SottocategoriaSoggetto sottocategoriaSoggetto;
	 

	@ManyToOne(targetEntity = Comune.class)
	@JoinColumn(name = "FK_DSOG_DCOM_ID", referencedColumnName = "SEQU_DCOM_ID")
	private Comune comune;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getCodiSoggettoTitolare() {
		return codiSoggettoTitolare;
	}


	public void setCodiSoggettoTitolare(String codiSoggettoTitolare) {
		this.codiSoggettoTitolare = codiSoggettoTitolare;
	}


	public String getDescSoggettoTitolare() {
		return descSoggettoTitolare;
	}


	public void setDescSoggettoTitolare(String descSoggettoTitolare) {
		this.descSoggettoTitolare = descSoggettoTitolare;
	}


	public String getCodiCodfiscalePiva() {
		return codiCodfiscalePiva;
	}


	public void setCodiCodfiscalePiva(String codiCodfiscalePiva) {
		this.codiCodfiscalePiva = codiCodfiscalePiva;
	}


	public String getTextIndirizzo() {
		return textIndirizzo;
	}


	public void setTextIndirizzo(String textIndirizzo) {
		this.textIndirizzo = textIndirizzo;
	}


	public String getTextTipoIndirizzo() {
		return textTipoIndirizzo;
	}


	public void setTextTipoIndirizzo(String textTipoIndirizzo) {
		this.textTipoIndirizzo = textTipoIndirizzo;
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


	public String getFlagConcentratore() {
		return flagConcentratore;
	}


	public void setFlagConcentratore(String flagConcentratore) {
		this.flagConcentratore = flagConcentratore;
	}


	public String getCodiEsterno() {
		return codiEsterno;
	}


	public void setCodiEsterno(String codiEsterno) {
		this.codiEsterno = codiEsterno;
	}


	public CategoriaSoggetto getCategoriaSoggetto() {
		return categoriaSoggetto;
	}


	public void setCategoriaSoggetto(CategoriaSoggetto categoriaSoggetto) {
		this.categoriaSoggetto = categoriaSoggetto;
	}


	public SottocategoriaSoggetto getSottocategoriaSoggetto() {
		return sottocategoriaSoggetto;
	}


	public void setSottocategoriaSoggetto(
			SottocategoriaSoggetto sottocategoriaSoggetto) {
		this.sottocategoriaSoggetto = sottocategoriaSoggetto;
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
		SoggettoTitolare other = (SoggettoTitolare) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 

}

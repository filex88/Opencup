package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "D_DMA_DCUP_ANAGRAFICA_CUP")
public class AnagraficaCup extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6010532916496227431L;

	@Id
	@Column(name = "SEQU_DCUP_ID")
	private Integer id;


	@Column(name = "CODI_DCUP_CUP", length = 15)
	private String codiCup;


	@Column(name = "DESC_DCUP_CUP", length = 4000)
	private String descCup;


	@Column(name = "DESC_DCUP_SOGGETTO_RICHIEDENTE", length = 50)
	private String descSoggettoRichiedente;


	@Column(name = "DESC_DCUP_TIPO_CUP", length = 20)
	private String descTipoCup;


	@Column(name = "DESC_DCUP_FINANZA_PROGETTO", length = 20)
	private String descFinanzaProgetto;


	@Column(name = "DESC_DCUP_SPONSORIZZAZIONI", length = 20)
	private String descSponsorizzazioni;


	@Column(name = "ANNO_DCUP_ANNO_DELIBERA", length = 4)
	private String annoAnnoDelibera;


	@Column(name = "NUME_DCUP_DELIBERA_CIPE", length = 10)
	private String numeDeliberaCipe;


	@Column(name = "FLAG_DCUP_LEGGE_OBIETTIVO", length = 1)
	private String flagLeggeObiettivo;


	@Column(name = "DESC_DCUP_RAGIONE_SOCIALE_AZIE", length = 255)
	private String descRagioneSocialeAzie;


	@Column(name = "DESC_DCUP_ATTO_AMMINISTRATIVO", length = 255)
	private String descAttoAmministrativo;


	@Column(name = "DESC_DCUP_MODALITA_INTERVENTO", length = 255)
	private String descModalitaIntervento;


	@Column(name = "DESC_DCUP_ENTE", length = 255)
	private String descEnte;


	@Column(name = "DESC_DCUP_BENE_SERVIZIO", length = 255)
	private String descBeneServizio;


	@Column(name = "DESC_DCUP_IMPRESA_STABILIMENTO", length = 255)
	private String descImpresaStabilimento;


	@Column(name = "TEXT_DCUP_INDIRIZZO", length = 110)
	private String textIndirizzo;


	@Column(name = "CODI_DCUP_PIVA", length = 16)
	private String codiPiva;


	@Column(name = "DESC_DCUP_OBIETTIVO_FINALE_INT", length = 255)
	private String descObiettivoFinaleInt;


	@Column(name = "CODI_DCUP_LOCALE_PROGETTO", length = 60)
	private String codiLocaleProgetto;


	@Column(name = "FLAG_DCUP_CUMULATIVO", length = 1)
	private String flagCumulativo;


	@Column(name = "DESC_DCUP_STRUMENTO", length = 255)
	private String descStrumento;


	@Column(name = "DESC_DCUP_BENEFICIARIO", length = 255)
	private String descBeneficiario;


	@Column(name = "FK_DCUP_DCUP_ID_MASTER")
	private Integer fkDcupDcupIdMaster;

	@ManyToOne(targetEntity = AnagraficaCup.class, cascade={CascadeType.ALL})
	@JoinColumn(name="FK_DCUP_DCUP_ID_MASTER", referencedColumnName = "SEQU_DCUP_ID", insertable = false, updatable = false)
	private AnagraficaCup anagraficaCup;


	@OneToMany(targetEntity=AnagraficaCup.class, fetch=FetchType.EAGER, mappedBy="anagraficaCup")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<AnagraficaCup> subordinatesCup;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiCup() {
		return codiCup;
	}

	public void setCodiCup(String codiCup) {
		this.codiCup = codiCup;
	}

	public String getDescCup() {
		return descCup;
	}

	public void setDescCup(String descCup) {
		this.descCup = descCup;
	}

	public String getDescSoggettoRichiedente() {
		return descSoggettoRichiedente;
	}

	public void setDescSoggettoRichiedente(String descSoggettoRichiedente) {
		this.descSoggettoRichiedente = descSoggettoRichiedente;
	}

	public String getDescTipoCup() {
		return descTipoCup;
	}

	public void setDescTipoCup(String descTipoCup) {
		this.descTipoCup = descTipoCup;
	}

	public String getDescFinanzaProgetto() {
		return descFinanzaProgetto;
	}

	public void setDescFinanzaProgetto(String descFinanzaProgetto) {
		this.descFinanzaProgetto = descFinanzaProgetto;
	}

	public String getDescSponsorizzazioni() {
		return descSponsorizzazioni;
	}

	public void setDescSponsorizzazioni(String descSponsorizzazioni) {
		this.descSponsorizzazioni = descSponsorizzazioni;
	}

	public String getAnnoAnnoDelibera() {
		return annoAnnoDelibera;
	}

	public void setAnnoAnnoDelibera(String annoAnnoDelibera) {
		this.annoAnnoDelibera = annoAnnoDelibera;
	}

	public String getNumeDeliberaCipe() {
		return numeDeliberaCipe;
	}

	public void setNumeDeliberaCipe(String numeDeliberaCipe) {
		this.numeDeliberaCipe = numeDeliberaCipe;
	}

	public String getFlagLeggeObiettivo() {
		return flagLeggeObiettivo;
	}

	public void setFlagLeggeObiettivo(String flagLeggeObiettivo) {
		this.flagLeggeObiettivo = flagLeggeObiettivo;
	}

	public String getDescRagioneSocialeAzie() {
		return descRagioneSocialeAzie;
	}

	public void setDescRagioneSocialeAzie(String descRagioneSocialeAzie) {
		this.descRagioneSocialeAzie = descRagioneSocialeAzie;
	}

	public String getDescAttoAmministrativo() {
		return descAttoAmministrativo;
	}

	public void setDescAttoAmministrativo(String descAttoAmministrativo) {
		this.descAttoAmministrativo = descAttoAmministrativo;
	}

	public String getDescModalitaIntervento() {
		return descModalitaIntervento;
	}

	public void setDescModalitaIntervento(String descModalitaIntervento) {
		this.descModalitaIntervento = descModalitaIntervento;
	}

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getDescBeneServizio() {
		return descBeneServizio;
	}

	public void setDescBeneServizio(String descBeneServizio) {
		this.descBeneServizio = descBeneServizio;
	}

	public String getDescImpresaStabilimento() {
		return descImpresaStabilimento;
	}

	public void setDescImpresaStabilimento(String descImpresaStabilimento) {
		this.descImpresaStabilimento = descImpresaStabilimento;
	}

	public String getTextIndirizzo() {
		return textIndirizzo;
	}

	public void setTextIndirizzo(String textIndirizzo) {
		this.textIndirizzo = textIndirizzo;
	}

	public String getCodiPiva() {
		return codiPiva;
	}

	public void setCodiPiva(String codiPiva) {
		this.codiPiva = codiPiva;
	}

	public String getDescObiettivoFinaleInt() {
		return descObiettivoFinaleInt;
	}

	public void setDescObiettivoFinaleInt(String descObiettivoFinaleInt) {
		this.descObiettivoFinaleInt = descObiettivoFinaleInt;
	}

	public String getCodiLocaleProgetto() {
		return codiLocaleProgetto;
	}

	public void setCodiLocaleProgetto(String codiLocaleProgetto) {
		this.codiLocaleProgetto = codiLocaleProgetto;
	}

	public String getFlagCumulativo() {
		return flagCumulativo;
	}

	public void setFlagCumulativo(String flagCumulativo) {
		this.flagCumulativo = flagCumulativo;
	}

	public String getDescStrumento() {
		return descStrumento;
	}

	public void setDescStrumento(String descStrumento) {
		this.descStrumento = descStrumento;
	}

	public String getDescBeneficiario() {
		return descBeneficiario;
	}

	public void setDescBeneficiario(String descBeneficiario) {
		this.descBeneficiario = descBeneficiario;
	}

	public Integer getFkDcupDcupIdMaster() {
		return fkDcupDcupIdMaster;
	}

	public void setFkDcupDcupIdMaster(Integer fkDcupDcupIdMaster) {
		this.fkDcupDcupIdMaster = fkDcupDcupIdMaster;
	}

	public AnagraficaCup getAnagraficaCup() {
		return anagraficaCup;
	}

	public void setAnagraficaCup(AnagraficaCup anagraficaCup) {
		this.anagraficaCup = anagraficaCup;
	}

	public List<AnagraficaCup> getSubordinatesCup() {
		return subordinatesCup;
	}

	public void setSubordinatesCup(List<AnagraficaCup> subordinatesCup) {
		this.subordinatesCup = subordinatesCup;
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
		AnagraficaCup other = (AnagraficaCup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}

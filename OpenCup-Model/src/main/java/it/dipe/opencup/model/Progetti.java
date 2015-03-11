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
@Table(name = "S_DMA_FPRG_PROGETTI")
public class Progetti extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4924415556609952598L;
	
	@Id
	@Column(name = "SEQU_FPRG_ID")
	private Integer id;

	@ManyToOne(targetEntity = AnnoDecisione.class)
	@JoinColumn(name = "FK_FPRG_DADE_ID", referencedColumnName = "SEQU_DADE_ID")
	private AnnoDecisione annoDecisione;

	@ManyToOne(targetEntity = SoggettoTitolare.class)
	@JoinColumn(name = "FK_FPRG_DSOG_ID", referencedColumnName = "SEQU_DSOG_ID")
	private SoggettoTitolare soggettoTitolare;

	@ManyToOne(targetEntity = UnitaOrganizzativa.class)
	@JoinColumn(name = "FK_FPRG_DUOR_ID", referencedColumnName = "SEQU_DUOR_ID")
	private UnitaOrganizzativa unitaOrganizzativa;

	@ManyToOne(targetEntity = Natura.class)
	@JoinColumn(name = "FK_FPRG_DNAT_ID", referencedColumnName = "SEQU_DNAT_ID")
	private Natura natura;
	
	@ManyToOne(targetEntity = TipologiaIntervento.class)
	@JoinColumn(name = "FK_FPRG_DTIN_ID", referencedColumnName = "SEQU_DTIN_ID")
	private TipologiaIntervento tipologiaIntervento;

	@ManyToOne(targetEntity = SettoreIntervento.class)
	@JoinColumn(name = "FK_FPRG_DSIN_ID", referencedColumnName = "SEQU_DSIN_ID")
	private SettoreIntervento settoreIntervento;

	@ManyToOne(targetEntity = SottosettoreIntervento.class)
	@JoinColumn(name = "FK_FPRG_DSSI_ID", referencedColumnName = "SEQU_DSSI_ID")
	private SottosettoreIntervento sottosettoreIntervento;
	
	@ManyToOne(targetEntity = CategoriaIntervento.class)
	@JoinColumn(name = "FK_FPRG_DCAI_ID", referencedColumnName = "SEQU_DCAI_ID")
	private CategoriaIntervento categoriaIntervento;
	
	@ManyToOne(targetEntity = StrumentoProgr.class)
	@JoinColumn(name = "FK_FPRG_DSTP_ID", referencedColumnName = "SEQU_DSTP_ID")
	private StrumentoProgr strumentoProgr;

	@ManyToOne(targetEntity = StatoProgetto.class)
	@JoinColumn(name = "FK_FPRG_DSPR_ID", referencedColumnName = "SEQU_DSPR_ID")
	private StatoProgetto statoProgetto;

	@Column(name = "IMPO_FPRG_COSTO_PROGETTO", columnDefinition = "NUMBER", precision = 15, scale = 3)
	private Double impoCostoProgetto;

	@Column(name = "IMPO_FPRG_IMPORTO_FINANZIATO", columnDefinition = "NUMBER", precision = 15, scale = 3)
	private Integer impoImportoFinanziato;

	@ManyToOne(targetEntity = GruppoAteco.class)
	@JoinColumn(name = "FK_FPRG_DGRA_ID", referencedColumnName = "SEQU_DGRA_ID")
	private GruppoAteco gruppoAteco;

	@ManyToOne(targetEntity = Cpv.class)
	@JoinColumn(name = "FK_FPRG_DCPV_ID", referencedColumnName = "SEQU_DCPV_ID")
	private Cpv cpv;

	@ManyToOne(targetEntity = AnagraficaCup.class)
	@JoinColumn(name = "FK_FPRG_DCUP_ID", referencedColumnName = "SEQU_DCUP_ID")
	private AnagraficaCup anagraficaCup;
	
	@ManyToOne(targetEntity = AreaIntervento.class)
	@JoinColumn(name = "FK_FPRG_DAIN_ID", referencedColumnName = "SEQU_DAIN_ID")
	private AreaIntervento areaIntervento;

//	@Column(name = "TEXT_FPRG_COPER_FINANZ", length = 255)
//	private String textCoperFinanz;
//
//	@Column(name = "TEXT_FPRG_COMUNE", length = 255)
//	private String textComune;
//
//	@Column(name = "TEXT_FPRG_PROVINCIA", length = 255)
//	private String textProvincia;
//
//	@Column(name = "TEXT_FPRG_REGIONE", length = 255)
//	private String textRegione;
//
//	@Column(name = "TEXT_FPRG_STATO", length = 255)
//	private String textStato;
//
//	@Column(name = "ANNO_FPRG_ANNO_DECISIONE", length = 4)
//	private String annoAnnoDecisione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AnnoDecisione getAnnoDecisione() {
		return annoDecisione;
	}

	public void setAnnoDecisione(AnnoDecisione annoDecisione) {
		this.annoDecisione = annoDecisione;
	}

	public SoggettoTitolare getSoggettoTitolare() {
		return soggettoTitolare;
	}

	public void setSoggettoTitolare(SoggettoTitolare soggettoTitolare) {
		this.soggettoTitolare = soggettoTitolare;
	}

	public UnitaOrganizzativa getUnitaOrganizzativa() {
		return unitaOrganizzativa;
	}

	public void setUnitaOrganizzativa(UnitaOrganizzativa unitaOrganizzativa) {
		this.unitaOrganizzativa = unitaOrganizzativa;
	}

	public Natura getNatura() {
		return natura;
	}

	public void setNatura(Natura natura) {
		this.natura = natura;
	}

	public TipologiaIntervento getTipologiaIntervento() {
		return tipologiaIntervento;
	}

	public void setTipologiaIntervento(TipologiaIntervento tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}

	public SettoreIntervento getSettoreIntervento() {
		return settoreIntervento;
	}

	public void setSettoreIntervento(SettoreIntervento settoreIntervento) {
		this.settoreIntervento = settoreIntervento;
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

	public StrumentoProgr getStrumentoProgr() {
		return strumentoProgr;
	}

	public void setStrumentoProgr(StrumentoProgr strumentoProgr) {
		this.strumentoProgr = strumentoProgr;
	}

	public StatoProgetto getStatoProgetto() {
		return statoProgetto;
	}

	public void setStatoProgetto(StatoProgetto statoProgetto) {
		this.statoProgetto = statoProgetto;
	}

	public Double getImpoCostoProgetto() {
		return impoCostoProgetto;
	}

	public void setImpoCostoProgetto(Double impoCostoProgetto) {
		this.impoCostoProgetto = impoCostoProgetto;
	}

	public Integer getImpoImportoFinanziato() {
		return impoImportoFinanziato;
	}

	public void setImpoImportoFinanziato(Integer impoImportoFinanziato) {
		this.impoImportoFinanziato = impoImportoFinanziato;
	}

	public GruppoAteco getGruppoAteco() {
		return gruppoAteco;
	}

	public void setGruppoAteco(GruppoAteco gruppoAteco) {
		this.gruppoAteco = gruppoAteco;
	}

	public Cpv getCpv() {
		return cpv;
	}

	public void setCpv(Cpv cpv) {
		this.cpv = cpv;
	}

	public AnagraficaCup getAnagraficaCup() {
		return anagraficaCup;
	}

	public void setAnagraficaCup(AnagraficaCup anagraficaCup) {
		this.anagraficaCup = anagraficaCup;
	}

//	public String getTextCoperFinanz() {
//		return textCoperFinanz;
//	}
//
//	public void setTextCoperFinanz(String textCoperFinanz) {
//		this.textCoperFinanz = textCoperFinanz;
//	}
//
//	public String getTextComune() {
//		return textComune;
//	}
//
//	public void setTextComune(String textComune) {
//		this.textComune = textComune;
//	}
//
//	public String getTextProvincia() {
//		return textProvincia;
//	}
//
//	public void setTextProvincia(String textProvincia) {
//		this.textProvincia = textProvincia;
//	}
//
//	public String getTextRegione() {
//		return textRegione;
//	}
//
//	public void setTextRegione(String textRegione) {
//		this.textRegione = textRegione;
//	}
//
//	public String getTextStato() {
//		return textStato;
//	}
//
//	public void setTextStato(String textStato) {
//		this.textStato = textStato;
//	}
//
//	public String getAnnoAnnoDecisione() {
//		return annoAnnoDecisione;
//	}
//
//	public void setAnnoAnnoDecisione(String annoAnnoDecisione) {
//		this.annoAnnoDecisione = annoAnnoDecisione;
//	}

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
		Progetti other = (Progetti) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}

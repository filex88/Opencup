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
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "S_DMA_FPRG_PROGETTI")
public class Progetto extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4924415556609952598L;
	
	@Id
	@Column(name = "SEQU_FPRG_ID")
	private Integer id;

	@ManyToOne(targetEntity = AnnoDecisione.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DADE_ID", referencedColumnName = "SEQU_DADE_ID")
	private AnnoDecisione annoDecisione;

	@ManyToOne(targetEntity = SoggettoTitolare.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DSOG_ID", referencedColumnName = "SEQU_DSOG_ID")
	private SoggettoTitolare soggettoTitolare;

	@ManyToOne(targetEntity = UnitaOrganizzativa.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DUOR_ID", referencedColumnName = "SEQU_DUOR_ID")
	private UnitaOrganizzativa unitaOrganizzativa;

	@ManyToOne(targetEntity = Natura.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DNAT_ID", referencedColumnName = "SEQU_DNAT_ID")
	private Natura natura;
	
	@ManyToOne(targetEntity = TipologiaIntervento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DTIN_ID", referencedColumnName = "SEQU_DTIN_ID")
	private TipologiaIntervento tipologiaIntervento;

	@ManyToOne(targetEntity = SettoreIntervento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DSIN_ID", referencedColumnName = "SEQU_DSIN_ID")
	private SettoreIntervento settoreIntervento;

	@ManyToOne(targetEntity = SottosettoreIntervento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DSSI_ID", referencedColumnName = "SEQU_DSSI_ID")
	private SottosettoreIntervento sottosettoreIntervento;
	
	@ManyToOne(targetEntity = CategoriaIntervento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DCAI_ID", referencedColumnName = "SEQU_DCAI_ID")
	private CategoriaIntervento categoriaIntervento;
	
	@ManyToOne(targetEntity = StrumentoProgr.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DSTP_ID", referencedColumnName = "SEQU_DSTP_ID")
	private StrumentoProgr strumentoProgr;

	@ManyToOne(targetEntity = StatoProgetto.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DSPR_ID", referencedColumnName = "SEQU_DSPR_ID")
	private StatoProgetto statoProgetto;
	
	@ManyToOne(targetEntity = GruppoAteco.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DGRA_ID", referencedColumnName = "SEQU_DGRA_ID")
	private GruppoAteco gruppoAteco;
	
	@ManyToOne(targetEntity = AnagraficaCup.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DCUP_ID", referencedColumnName = "SEQU_DCUP_ID")
	private AnagraficaCup anagraficaCup;

	@ManyToOne(targetEntity = AreaIntervento.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_FPRG_DAIN_ID", referencedColumnName = "SEQU_DAIN_ID")
	private AreaIntervento areaIntervento;
	
	@Column(name = "IMPO_FPRG_COSTO_PROGETTO", columnDefinition = "NUMBER", precision = 15, scale = 3)
	private Double impoCostoProgetto;

	@Column(name = "IMPO_FPRG_IMPORTO_FINANZIATO", columnDefinition = "NUMBER", precision = 15, scale = 3)
	private Double impoImportoFinanziato;

	@Column(name = "ANNO_FPRG_ANNO_DECISIONE", length = 4)
	private String annoAnnoDecisione;
	
	@Transient
	private String dettaglioUrl;
	
	@Transient
	private String comuniProgetto;
	
	public void setComuniProgetto(String comuniProgetto) {
		this.comuniProgetto = comuniProgetto;
	}

	public String getComuniProgetto(){
		if (comuniProgetto == null) {
		String comuniProgettoLocal = "";
			for( CupLocalizzazione c : this.anagraficaCup.getCupLocalizzazioneList() ){
				if( comuniProgettoLocal.toLowerCase().indexOf( c.getComune().getDescComune().toLowerCase() ) == -1 ){
					comuniProgettoLocal = ( StringUtils.isEmpty(comuniProgettoLocal) )? c.getComune().getDescComune() : comuniProgettoLocal + ", " + c.getComune().getDescComune();
				}
			}
			return comuniProgettoLocal;
		}
		return comuniProgetto;
	}

	public String getProvinceProgetto(){
		String provinceProgetto = "";
		for( CupLocalizzazione c : this.anagraficaCup.getCupLocalizzazioneList() ){
			if( provinceProgetto.toLowerCase().indexOf( c.getProvincia().getDescProvincia().toLowerCase() ) == -1 ){
				provinceProgetto = ( StringUtils.isEmpty(provinceProgetto) )? c.getProvincia().getDescProvincia() : provinceProgetto + ", " + c.getProvincia().getDescProvincia();
			}
		}
		return provinceProgetto;
	}
	
	public String getRegioneProgetto(){
		String regioneProgetto = "";
		for( CupLocalizzazione c : this.anagraficaCup.getCupLocalizzazioneList() ){
			if( regioneProgetto.toLowerCase().indexOf( c.getRegione().getDescRegione().toLowerCase() ) == -1 ){
				regioneProgetto = ( StringUtils.isEmpty(regioneProgetto) )? c.getRegione().getDescRegione() : regioneProgetto + ", " + c.getRegione().getDescRegione();
			}
		}
		return regioneProgetto;
	}
	
	public String getAreaGeografica(){
		String areaGeografica = "";
		for( CupLocalizzazione c : this.anagraficaCup.getCupLocalizzazioneList() ){
			if( areaGeografica.toLowerCase().indexOf( c.getAreaGeografica().getDescAreaGeografica().toLowerCase() ) == -1 ){
				areaGeografica = ( StringUtils.isEmpty(areaGeografica) )? c.getAreaGeografica().getDescAreaGeografica() : areaGeografica + ", " + c.getAreaGeografica().getDescAreaGeografica();
			}
		}
		return areaGeografica;
	}

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

	public GruppoAteco getGruppoAteco() {
		return gruppoAteco;
	}

	public void setGruppoAteco(GruppoAteco gruppoAteco) {
		this.gruppoAteco = gruppoAteco;
	}

	public AnagraficaCup getAnagraficaCup() {
		return anagraficaCup;
	}

	public void setAnagraficaCup(AnagraficaCup anagraficaCup) {
		this.anagraficaCup = anagraficaCup;
	}

	public AreaIntervento getAreaIntervento() {
		return areaIntervento;
	}

	public void setAreaIntervento(AreaIntervento areaIntervento) {
		this.areaIntervento = areaIntervento;
	}

	public Double getImpoCostoProgetto() {
		return impoCostoProgetto;
	}

	public void setImpoCostoProgetto(Double impoCostoProgetto) {
		this.impoCostoProgetto = impoCostoProgetto;
	}

	public Double getImpoImportoFinanziato() {
		return impoImportoFinanziato;
	}

	public void setImpoImportoFinanziato(Double impoImportoFinanziato) {
		this.impoImportoFinanziato = impoImportoFinanziato;
	}

	public String getAnnoAnnoDecisione() {
		return annoAnnoDecisione;
	}

	public void setAnnoAnnoDecisione(String annoAnnoDecisione) {
		this.annoAnnoDecisione = annoAnnoDecisione;
	}
	
	public String getDettaglioUrl() {
		return dettaglioUrl;
	}

	public void setDettaglioUrl(String dettaglioUrl) {
		this.dettaglioUrl = dettaglioUrl;
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
		Progetto other = (Progetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



}

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
@Table(name = "S_DMA_FAGG_AGGREGATA")
public class Aggregata extends AbstractCommonEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2621837735327804843L;

	@Id
	@Column(name = "SEQU_FAGG_ID")
	private Integer id;
	 

	@Column(name = "NUME_FAGG_PROGETTI")
	private Integer numeProgetti;
	 

	@Column(name = "IMPO_FAGG_COSTO_PROGETTI", columnDefinition = "NUMBER", precision = 15, scale = 3)
	private Double impoCostoProgetti;
	 

	@Column(name = "IMPO_FAGG_IMPORTO_FINANZIATO", columnDefinition = "NUMBER", precision = 15, scale = 3)
	private Double impoImportoFinanziato;
	
	
	@ManyToOne(targetEntity = Classificazione.class)
	@JoinColumn(name = "FK_FAGG_DCLA_ID", referencedColumnName = "SEQU_DCLA_ID")
	private Classificazione classificazione;
	
	 
	@ManyToOne(targetEntity = Localizzazione.class)
	@JoinColumn(name = "FK_FAGG_DLOC_ID", referencedColumnName = "SEQU_DLOC_ID")
	private Localizzazione localizzazione;
	
	
	@ManyToOne(targetEntity = GerarchiaSoggetto.class)
	@JoinColumn(name = "FK_FAGG_DGSO_ID", referencedColumnName = "SEQU_DGSO_ID")
	private GerarchiaSoggetto gerarchiaSoggetto;
	

	@ManyToOne(targetEntity = TipologiaIntervento.class)
	@JoinColumn(name = "FK_FAGG_DTIN_ID", referencedColumnName = "SEQU_DTIN_ID")
	private TipologiaIntervento tipologiaIntervento;
	
	
	@ManyToOne(targetEntity = StatoProgetto.class)
	@JoinColumn(name = "FK_FAGG_DSPR_ID", referencedColumnName = "SEQU_DSPR_ID")
	private StatoProgetto statoProgetto;
	
	@ManyToOne(targetEntity = AnnoAggregato.class)
	@JoinColumn(name = "FK_FAGG_DAAG_ID", referencedColumnName = "SEQU_DAAG_ID")
	private AnnoAggregato annoAggregato;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getNumeProgetti() {
		return numeProgetti;
	}


	public void setNumeProgetti(Integer numeProgetti) {
		this.numeProgetti = numeProgetti;
	}


	public Double getImpoCostoProgetti() {
		return impoCostoProgetti;
	}


	public void setImpoCostoProgetti(Double impoCostoProgetti) {
		this.impoCostoProgetti = impoCostoProgetti;
	}


	public Double getImpoImportoFinanziato() {
		return impoImportoFinanziato;
	}


	public void setImpoImportoFinanziato(Double impoImportoFinanziato) {
		this.impoImportoFinanziato = impoImportoFinanziato;
	}


	public Classificazione getClassificazione() {
		return classificazione;
	}


	public void setClassificazione(Classificazione classificazione) {
		this.classificazione = classificazione;
	}


	public Localizzazione getLocalizzazione() {
		return localizzazione;
	}


	public void setLocalizzazione(Localizzazione localizzazione) {
		this.localizzazione = localizzazione;
	}


	public GerarchiaSoggetto getGerarchiaSoggetto() {
		return gerarchiaSoggetto;
	}


	public void setGerarchiaSoggetto(GerarchiaSoggetto gerarchiaSoggetto) {
		this.gerarchiaSoggetto = gerarchiaSoggetto;
	}


	public TipologiaIntervento getTipologiaIntervento() {
		return tipologiaIntervento;
	}


	public void setTipologiaIntervento(TipologiaIntervento tipologiaIntervento) {
		this.tipologiaIntervento = tipologiaIntervento;
	}


	public StatoProgetto getStatoProgetto() {
		return statoProgetto;
	}


	public void setStatoProgetto(StatoProgetto statoProgetto) {
		this.statoProgetto = statoProgetto;
	}


	public AnnoAggregato getAnnoAggregato() {
		return annoAggregato;
	}


	public void setAnnoAggregato(AnnoAggregato annoAggregato) {
		this.annoAggregato = annoAggregato;
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
		Aggregata other = (Aggregata) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}

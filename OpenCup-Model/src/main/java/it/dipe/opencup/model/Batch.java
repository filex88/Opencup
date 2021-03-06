package it.dipe.opencup.model;

import it.dipe.opencup.model.common.AbstractCommonEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "D_SNA_LIFB_BATCH")
public class Batch extends AbstractCommonEntity implements Serializable {

	private static final long serialVersionUID = -1741849034438391979L;


	@GenericGenerator(name = "generator", strategy = "sequence-identity", parameters = @Parameter(name = "sequence", value = "SEQU_LIFB"))
	@GeneratedValue(generator = "generator")
	@Id
	@Column(name = "SEQU_LIFB_ID")
	private Integer id;
	 

	@Column(name = "TEXT_LIFB_NOME")
	private String nome;
	 

	@Column(name = "TEXT_LIFB_DESCRIZIONE")
	private String descrizione;
	 
	@Column(name = "TEXT_LIFB_CRON")
	private String cron;
	 
	@Column(name = "NUME_LIFB_TOTALE")
	private Integer totale;
	
	@Column(name = "NUME_LIFB_STEP")
	private Integer step;
	
	@Column(name = "TEXT_LIFB_STATO")
	private String stato;
	
	@Column(name = "DATA_LIFB_ULTIMA_ESECUZIONE")
	private Date dataUltimaEsecuzione;
	
	@Version
	@Column(name = "NUME_LIFB_VERSIONE")
	private Integer versione;
	
	@Transient
	private String prossimaEsecuzione;
	
	@Transient
	private Date precedenteEsecuzione;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Integer getTotale() {
		return totale;
	}

	public void setTotale(Integer totale) {
		this.totale = totale;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getVersione() {
		return versione;
	}

	public void setVersione(Integer versione) {
		this.versione = versione;
	}
	

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public String getProssimaEsecuzione() {
		return prossimaEsecuzione;
	}

	public void setProssimaEsecuzione(String prossimaEsecuzione) {
		this.prossimaEsecuzione = prossimaEsecuzione;
	}

	public Date getPrecedenteEsecuzione() {
		return precedenteEsecuzione;
	}

	public void setPrecedenteEsecuzione(Date precedenteEsecuzione) {
		this.precedenteEsecuzione = precedenteEsecuzione;
	}

	public Date getDataUltimaEsecuzione() {
		return dataUltimaEsecuzione;
	}

	public void setDataUltimaEsecuzione(Date dataUltimaEsecuzione) {
		this.dataUltimaEsecuzione = dataUltimaEsecuzione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cron == null) ? 0 : cron.hashCode());
		result = prime * result
				+ ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		result = prime * result + ((totale == null) ? 0 : totale.hashCode());
		result = prime * result
				+ ((versione == null) ? 0 : versione.hashCode());
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
		Batch other = (Batch) obj;
		if (cron == null) {
			if (other.cron != null)
				return false;
		} else if (!cron.equals(other.cron))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		if (step == null) {
			if (other.step != null)
				return false;
		} else if (!step.equals(other.step))
			return false;
		if (totale == null) {
			if (other.totale != null)
				return false;
		} else if (!totale.equals(other.totale))
			return false;
		if (versione == null) {
			if (other.versione != null)
				return false;
		} else if (!versione.equals(other.versione))
			return false;
		return true;
	}


	
}

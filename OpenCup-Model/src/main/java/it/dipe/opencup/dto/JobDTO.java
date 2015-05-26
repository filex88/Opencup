package it.dipe.opencup.dto;

import java.io.Serializable;
import java.util.Date;

public class JobDTO implements Serializable {

	private static final long serialVersionUID = 8318960693723030053L;
	
	private String stato;
	
	private String cronExp;
	
	private String prossimaEsecuzione;
	
	private Date precedenteEsecuzione;
	
	private String totale;
	
	private String step;
	
	public Date getPrecedenteEsecuzione() {
		return precedenteEsecuzione;
	}

	public void setPrecedenteEsecuzione(Date precedenteEsecuzione) {
		this.precedenteEsecuzione = precedenteEsecuzione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getCronExp() {
		return cronExp;
	}

	public void setCronExp(String cronExp) {
		this.cronExp = cronExp;
	}

	public String getProssimaEsecuzione() {
		return prossimaEsecuzione;
	}

	public void setProssimaEsecuzione(String prossimaEsecuzione) {
		this.prossimaEsecuzione = prossimaEsecuzione;
	}

	public String getTotale() {
		return totale;
	}

	public void setTotale(String totale) {
		this.totale = totale;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	
	
}

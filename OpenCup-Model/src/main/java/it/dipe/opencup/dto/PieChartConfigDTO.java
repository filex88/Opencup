package it.dipe.opencup.dto;

import java.io.Serializable;

public class PieChartConfigDTO implements Serializable {

	private static final long serialVersionUID = -2276042836988266597L;
	
	public static final String PROP_MOSTRAPULTANTI = "MOSTRA_PULSANTI";
	public static final String PROP_SELEZIONABILE = "SELEZIONABILE";

	private boolean mostraPulsanti;
	
	private boolean selezionabile;

	public boolean isMostraPulsanti() {
		return mostraPulsanti;
	}

	public void setMostraPulsanti(boolean mostraPulsanti) {
		this.mostraPulsanti = mostraPulsanti;
	}

	public boolean isSelezionabile() {
		return selezionabile;
	}

	public void setSelezionabile(boolean selezionabile) {
		this.selezionabile = selezionabile;
	}
	
	
}

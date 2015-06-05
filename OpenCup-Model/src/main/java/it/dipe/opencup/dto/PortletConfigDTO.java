package it.dipe.opencup.dto;

import java.io.Serializable;

public class PortletConfigDTO implements Serializable {

	private static final long serialVersionUID = -2276042836988266597L;
	
	public static final String PROP_MOSTRAPULTANTI = "MOSTRA_PULSANTI";
	public static final String PROP_SELEZIONABILE = "SELEZIONABILE";
	public static final String PROP_PORTLET_PRINCIPALE = "PORTLET_PRINCIPALE";
	public static final String PROP_PORTLET_SECONDARIA_DX = "PROP_PORTLET_SECONDARIA_DX";
	
	private boolean portletPrincipale;
	
	private boolean mostraPulsanti;
	
	private boolean selezionabile;
	
	private boolean portletSecondariaDX;
	
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

	public boolean isPortletPrincipale() {
		return portletPrincipale;
	}

	public void setPortletPrincipale(boolean portletPrincipale) {
		this.portletPrincipale = portletPrincipale;
	}

	public boolean isPortletSecondariaDX() {
		return portletSecondariaDX;
	}

	public void setPortletSecondariaDX(boolean portletSecondariaDX) {
		this.portletSecondariaDX = portletSecondariaDX;
	}

}

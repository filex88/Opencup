package it.dipe.opencup.dto;

import java.io.Serializable;

public class LocalizationValueConverter implements Serializable{

	private static final long serialVersionUID = -4831831295847632970L;
	
	private String localizationLabel;
	private double localizationValue;
	
	
	public String getLocalizationLabel() {
		return localizationLabel;
	}
	public void setLocalizationLabel(String localizationLabel) {
		this.localizationLabel = localizationLabel;
	}
	public double getLocalizationValue() {
		return localizationValue;
	}
	public void setLocalizationValue(double localizationValue) {
		this.localizationValue = localizationValue;
	}

}

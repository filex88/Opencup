package it.dipe.opencup.dto;

import java.io.Serializable;

public class LocalizationValueConverter implements Serializable{

	private static final long serialVersionUID = -4831831295847632970L;
	
	private String localizationLabel;
	private double localizationValue;
	private String detailUrl;
	
	public LocalizationValueConverter() {
	}
	public LocalizationValueConverter(String localizationLabel,double localizationValue, String detailUrl) {
		this.localizationLabel = localizationLabel;
		this.localizationValue = localizationValue;
		this.detailUrl = detailUrl;
	}
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
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

}

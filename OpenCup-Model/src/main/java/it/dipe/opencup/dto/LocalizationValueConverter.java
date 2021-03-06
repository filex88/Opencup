package it.dipe.opencup.dto;

import java.io.Serializable;

public class LocalizationValueConverter implements Serializable{

	private static final long serialVersionUID = -4831831295847632970L;
	
	private String localizationLabel;
	private double costoValue;
	private double importoValue;
	private Long volumeValue;
	private String detailUrl;
	private String fullLabel;
	private String linkMatch;
	
	public LocalizationValueConverter() {
	}
	public LocalizationValueConverter(String localizationLabel,double importoValue,double costoValue,
			Long volumeValue,  String detailUrl,String fullLabel,String linkMatch) {
		this.localizationLabel = localizationLabel;
		this.importoValue = importoValue;
		this.costoValue=costoValue;
		this.volumeValue=volumeValue;
		this.detailUrl = detailUrl;
		this.fullLabel=fullLabel;
	}
	public String getLocalizationLabel() {
		return localizationLabel;
	}
	public void setLocalizationLabel(String localizationLabel) {
		this.localizationLabel = localizationLabel;
	}
	
	public double getCostoValue() {
		return costoValue;
	}
	public void setCostoValue(double costoValue) {
		this.costoValue = costoValue;
	}
	public double getImportoValue() {
		return importoValue;
	}
	public void setImportoValue(double importoValue) {
		this.importoValue = importoValue;
	}
	public Long getVolumeValue() {
		return volumeValue;
	}
	public void setVolumeValue(Long volumeValue) {
		this.volumeValue = volumeValue;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getFullLabel() {
		return fullLabel;
	}
	public void setFullLabel(String fullLabel) {
		this.fullLabel = fullLabel;
	}
	public String getLinkMatch() {
		return linkMatch;
	}
	public void setLinkMatch(String linkMatch) {
		this.linkMatch = linkMatch;
	}
	
}

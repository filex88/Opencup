package it.dipe.opencup.dto;


import java.io.Serializable;

public class D3PieConverter implements Serializable{

	private static final long serialVersionUID = -7162369296034046712L;
	private String id = "0";
	private String label = "";
	private double value = 0.0;
	private String formatedValue = "";
	private String percentage = "0%";
	private String linkURL = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getLinkURL() {
		return linkURL;
	}
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	public String getFormatedValue() {
		return formatedValue;
	}
	public void setFormatedValue(String formatedValue) {
		this.formatedValue = formatedValue;
	}
}

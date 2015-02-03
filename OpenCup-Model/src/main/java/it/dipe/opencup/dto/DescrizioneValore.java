package it.dipe.opencup.dto;

import java.io.Serializable;

public class DescrizioneValore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1981276594506897407L;
	
	private String label;
	private Double value;
	
	public DescrizioneValore(){
		
	}
	
	public DescrizioneValore(String label, Double value){
		this.label = label;
		this.value = value;
	}
	
	public DescrizioneValore(String label, Integer value){
		this.label = label;
		this.value = value.doubleValue();
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	

}

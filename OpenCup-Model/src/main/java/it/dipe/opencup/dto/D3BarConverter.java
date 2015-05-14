package it.dipe.opencup.dto;

import java.io.Serializable;

public class D3BarConverter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6442036319227357618L;
	
	private String label;
	private Long volume;
	private String linkURL;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	public String getLinkURL() {
		return linkURL;
	}
	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	
	
}

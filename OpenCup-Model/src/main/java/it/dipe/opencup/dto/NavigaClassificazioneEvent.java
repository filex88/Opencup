package it.dipe.opencup.dto;

import java.io.Serializable;

public class NavigaClassificazioneEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 143973242554717243L;
	
	private String rowIdLiv1 = "";
	private String rowIdLiv2 = "";
	private String rowIdLiv3 = "";
	private String rowIdLiv4 = "";
	public String getRowIdLiv1() {
		return rowIdLiv1;
	}
	public void setRowIdLiv1(String rowIdLiv1) {
		this.rowIdLiv1 = rowIdLiv1;
	}
	public String getRowIdLiv2() {
		return rowIdLiv2;
	}
	public void setRowIdLiv2(String rowIdLiv2) {
		this.rowIdLiv2 = rowIdLiv2;
	}
	public String getRowIdLiv3() {
		return rowIdLiv3;
	}
	public void setRowIdLiv3(String rowIdLiv3) {
		this.rowIdLiv3 = rowIdLiv3;
	}
	public String getRowIdLiv4() {
		return rowIdLiv4;
	}
	public void setRowIdLiv4(String rowIdLiv4) {
		this.rowIdLiv4 = rowIdLiv4;
	}
	
	
	
}

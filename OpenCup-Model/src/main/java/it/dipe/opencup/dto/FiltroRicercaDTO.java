package it.dipe.opencup.dto;

import java.io.Serializable;

public class FiltroRicercaDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7513464291476821898L;


	private Integer id;
	private String label;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}

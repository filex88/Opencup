package it.dipe.opencup.dto;

import java.io.Serializable;

public class DocumentoDTO implements Serializable {


	private static final long serialVersionUID = -6918827085155149734L;

	private String titolo;
	private String url;
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}

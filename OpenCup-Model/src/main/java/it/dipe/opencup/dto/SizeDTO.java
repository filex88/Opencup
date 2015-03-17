package it.dipe.opencup.dto;

import java.io.Serializable;

public class SizeDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2139457365528853785L;
	
	private int size;

	public SizeDTO(){
		
	}
	
	public SizeDTO(int size){
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}

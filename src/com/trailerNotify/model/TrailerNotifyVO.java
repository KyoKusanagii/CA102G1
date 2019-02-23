package com.trailerNotify.model;


import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class TrailerNotifyVO implements Serializable {
	private String trailer_No;
	private String mem_No;
	
	public String getTrailer_No() {
		return trailer_No;
	}
	public void setTrailer_No(String trailer_No) {
		this.trailer_No = trailer_No;
	}
	public String getMem_No() {
		return mem_No;
	}
	public void setMem_No(String mem_No) {
		this.mem_No = mem_No;
	}
	
	
	
	
	
	
}

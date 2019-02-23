package com.follow_mem.model;

import java.io.Serializable;
import java.sql.Date;

public class Follow_memVO implements Serializable{
	private String folo_mem_no;
	private String foloed_mem_no;
	private Date folo_time;
	
	public Follow_memVO() {
		
	}
	public String getFolo_mem_no() {
		return folo_mem_no;
	}
	public void setFolo_mem_no(String folo_mem_no) {
		this.folo_mem_no = folo_mem_no;
	}
	public String getFoloed_mem_no() {
		return foloed_mem_no;
	}
	public void setFoloed_mem_no(String foloed_mem_no) {
		this.foloed_mem_no = foloed_mem_no;
	}
	public Date getFolo_time() {
		return folo_time;
	}
	public void setFolo_time(Date folo_time) {
		this.folo_time = folo_time;
	}
	

}

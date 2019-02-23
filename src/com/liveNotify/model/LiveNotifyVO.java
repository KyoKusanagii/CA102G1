package com.liveNotify.model;


import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class LiveNotifyVO implements Serializable {
	private String live_no;
	private String mem_No;
	public String getLive_no() {
		return live_no;
	}
	public void setLive_no(String live_no) {
		this.live_no = live_no;
	}
	public String getMem_No() {
		return mem_No;
	}
	public void setMem_No(String mem_No) {
		this.mem_No = mem_No;
	}
	
		
	
}

package com.arti_reply.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Arti_replyVO implements Serializable{
	private String arti_no;
	private Integer rep_no;
	private String mem_no;
	private String rep_content;
	private Timestamp rep_time;
	
	public Arti_replyVO() {
		
	}
	public String getArti_no() {
		return arti_no;
	}
	public void setArti_no(String arti_no) {
		this.arti_no = arti_no;
	}
	public Integer getRep_no() {
		return rep_no;
	}
	public void setRep_no(Integer rep_no) {
		this.rep_no = rep_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getRep_content() {
		return rep_content;
	}
	public void setRep_content(String rep_content) {
		this.rep_content = rep_content;
	}
	public Timestamp getRep_time() {
		return rep_time;
	}
	public void setRep_time(Timestamp rep_time) {
		this.rep_time = rep_time;
	}
	
	
}

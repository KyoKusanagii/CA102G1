package com.article.model;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ArticleVO implements Serializable{
	
	private String arti_no;
	private String arti_topic;
	private String mem_no;
	private String arti_content;
	private Timestamp po_time;
	
	public ArticleVO() {
		
	}
	
	public String getArti_no() {
		return arti_no;
	}
	public void setArti_no(String arti_no) {
		this.arti_no = arti_no;
	}
	public String getArti_topic() {
		return arti_topic;
	}
	public void setArti_topic(String arti_topic) {
		this.arti_topic = arti_topic;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getArti_content() {
		return arti_content;
	}
	public void setArti_content(String arti_content) {
		this.arti_content = arti_content;
	}
	public Timestamp getPo_time() {
		return po_time;
	}
	public void setPo_time(Timestamp po_time) {
		this.po_time = po_time;
	}


}



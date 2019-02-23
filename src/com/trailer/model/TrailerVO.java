package com.trailer.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class TrailerVO implements Serializable {
	
	private String trailer_no;		    	
	private String trailer_seller_no;
	private Timestamp trailer_time;	
	private String trailer_topic;
	
	public TrailerVO() {
		
	}
	
	public TrailerVO(String trailer_no, String trailer_seller_no, Timestamp trailer_time, 
			String trailer_topic) {
		this.trailer_no = trailer_no;
		this.trailer_seller_no = trailer_seller_no;
		this.trailer_time = trailer_time;
		this.trailer_topic = trailer_topic;
	}
	public String getTrailer_no() {
		return trailer_no;
	}
	public void setTrailer_no(String trailer_no) {
		this.trailer_no = trailer_no;
	}
	public String getTrailer_seller_no() {
		return trailer_seller_no;
	}
	public void setTrailer_seller_no(String trailer_seller_no) {
		this.trailer_seller_no = trailer_seller_no;
	}
	public Timestamp getTrailer_time() {
		return trailer_time;
	}
	public void setTrailer_time(Timestamp trailer_time) {
		this.trailer_time = trailer_time;
	}
	public String getTrailer_topic() {
		return trailer_topic;
	}
	public void setTrailer_topic(String trailer_topic) {
		this.trailer_topic = trailer_topic;
	}
	
	

}

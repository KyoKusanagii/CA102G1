package com.live.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LiveVO implements Serializable {
	
	private String live_no;
	private String live_seller_no;
	private String live_address;
	private Integer live_status;
	private Timestamp live_start_time;
	private Timestamp live_end_time;
	
	public LiveVO() {
		
	}
	
	public LiveVO(String live_no, String live_seller_no, String live_address, Integer live_status, Timestamp live_start_time,
			Timestamp live_end_time) {
		this.live_no = live_no;
		this.live_seller_no = live_seller_no;
		this.live_address = live_address;
		this.live_status = live_status;
		this.live_start_time = live_start_time;
		this.live_end_time = live_end_time;
	}
	public String getLive_no() {
		return live_no;
	}
	public void setLive_no(String live_no) {
		this.live_no = live_no;
	}
	public String getLive_seller_no() {
		return live_seller_no;
	}
	public void setLive_seller_no(String live_seller_no) {
		this.live_seller_no = live_seller_no;
	}
	public String getLive_address() {
		return live_address;
	}
	public void setLive_address(String live_address) {
		this.live_address = live_address;
	}
	public Integer getLive_status() {
		return live_status;
	}
	public void setLive_status(Integer live_status) {
		this.live_status = live_status;
	}
	public Timestamp getLive_start_time() {
		return live_start_time;
	}
	public void setLive_start_time(Timestamp live_start_time) {
		this.live_start_time = live_start_time;
	}
	public Timestamp getLive_end_time() {
		return live_end_time;
	}
	public void setLive_end_time(Timestamp live_end_time) {
		this.live_end_time = live_end_time;
	}
	


}

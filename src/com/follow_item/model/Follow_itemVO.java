package com.follow_item.model;

import java.io.Serializable;
import java.sql.Date;

public class Follow_itemVO implements Serializable{
	private String mem_no;
	private String item_no;
	private Date folo_time;
	
	public Follow_itemVO() {
		
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getItem_no() {
		return item_no;
	}
	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}
	public Date getFolo_time() {
		return folo_time;
	}
	public void setFolo_time(Date folo_time) {
		this.folo_time = folo_time;
	}

}

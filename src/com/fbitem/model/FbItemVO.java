package com.fbitem.model;

import java.io.Serializable;

public class FbItemVO implements Serializable {
	
	private String fb_order_no;
	private String item_no;
	private byte[] fb_item_pic;
	private Integer fb_item_cnt;
	private Integer fb_item_prc;
	private String  fb_comment;
		
	

	
	public FbItemVO(String fb_order_no, String item_no, byte[] fb_item_pic, Integer fb_item_cnt, Integer fb_item_prc) {
		this.fb_order_no = fb_order_no;
		this.item_no = item_no;
		this.fb_item_pic = fb_item_pic;
		this.fb_item_cnt = fb_item_cnt;
		this.fb_item_prc = fb_item_prc;
	}
	public String getFb_order_no() {
		return fb_order_no;
	}
	public void setFb_order_no(String fb_order_no) {
		this.fb_order_no = fb_order_no;
	}
	public String getItem_no() {
		return item_no;
	}
	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}
	public byte[] getFb_item_pic() {
		return fb_item_pic;
	}
	public void setFb_item_pic(byte[] fb_item_pic) {
		this.fb_item_pic = fb_item_pic;
	}
	public Integer getFb_item_cnt() {
		return fb_item_cnt;
	}
	public void setFb_item_cnt(Integer fb_item_cnt) {
		this.fb_item_cnt = fb_item_cnt;
	}
	public Integer getFb_item_prc() {
		return fb_item_prc;
	}
	public void setFb_item_prc(Integer fb_item_prc) {
		this.fb_item_prc = fb_item_prc;
	}
	public String getFb_comment() {
		return fb_comment;
	}

	public void setFb_comment(String fb_comment) {
		this.fb_comment = fb_comment;
	}

	public FbItemVO() {
		
	}
	

}

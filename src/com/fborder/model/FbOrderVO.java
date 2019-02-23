package com.fborder.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FbOrderVO implements Serializable {

	private String fb_order_no;
	private String fb_buyer_no;
	private Timestamp fb_order_time;
	private Integer fb_order_prc;
	private String fb_order_trans;
	private String fb_order_status;
	private String fb_pay_status;
	private String fb_order_remark;
	// 20180730 for ½æ®a­q³æ¬d¸ß
	private String fb_item_owner;
	private Integer fb_item_count;
	private String fb_transport;

	public FbOrderVO() {

	}

	public FbOrderVO(String fb_order_no, String fb_buyer_no, Timestamp fb_order_time, String fb_comment,
			Integer fb_order_prc, String fb_order_trans, String fb_order_status, String fb_pay_status,
			String fb_order_remark, String fb_item_owner, Integer fb_item_count) {
		this.fb_order_no = fb_order_no;
		this.fb_buyer_no = fb_buyer_no;
		this.fb_order_time = fb_order_time;
		this.fb_order_prc = fb_order_prc;
		this.fb_order_trans = fb_order_trans;
		this.fb_order_status = fb_order_status;
		this.fb_pay_status = fb_pay_status;
		this.fb_order_remark = fb_order_remark;
		this.fb_item_owner = fb_item_owner;
		this.fb_item_count = fb_item_count;
	}

	public String getFb_transport() {
		return fb_transport;
	}

	public void setFb_transport(String fb_transport) {
		this.fb_transport = fb_transport;
	}

	public String getFb_order_no() {
		return fb_order_no;
	}

	public void setFb_order_no(String fb_order_no) {
		this.fb_order_no = fb_order_no;
	}

	public String getFb_buyer_no() {
		return fb_buyer_no;
	}

	public void setFb_buyer_no(String fb_buyer_no) {
		this.fb_buyer_no = fb_buyer_no;
	}

	public Timestamp getFb_order_time() {
		return fb_order_time;
	}

	public void setFb_order_time(Timestamp fb_order_time) {
		this.fb_order_time = fb_order_time;
	}

	public Integer getFb_order_prc() {
		return fb_order_prc;
	}

	public void setFb_order_prc(Integer fb_order_prc) {
		this.fb_order_prc = fb_order_prc;
	}

	public String getFb_order_trans() {
		return fb_order_trans;
	}

	public void setFb_order_trans(String fb_order_trans) {
		this.fb_order_trans = fb_order_trans;
	}

	public String getFb_order_status() {
		return fb_order_status;
	}

	public void setFb_order_status(String fb_order_status) {
		this.fb_order_status = fb_order_status;
	}

	public String getFb_pay_status() {
		return fb_pay_status;
	}

	public void setFb_pay_status(String fb_pay_status) {
		this.fb_pay_status = fb_pay_status;
	}

	public String getFb_order_remark() {
		return fb_order_remark;
	}

	public void setFb_order_remark(String fb_order_remark) {
		this.fb_order_remark = fb_order_remark;
	}

	public String getFb_item_owner() {
		return fb_item_owner;
	}

	public void setFb_item_owner(String fb_item_owner) {
		this.fb_item_owner = fb_item_owner;
	}

	public Integer getFb_item_count() {
		return fb_item_count;
	}

	public void setFb_item_count(Integer fb_item_count) {
		this.fb_item_count = fb_item_count;
	}
	

}

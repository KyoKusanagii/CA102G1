package com.android.model;

import java.sql.Timestamp;

public class BuyerOrderVO {
	private String item_no;
	private Timestamp order_time;
	private String order_no;
	private String buyer_no;
	private Integer order_prc;
	private String order_trans;
	private String order_status;
	private String pay_status;
	private String order_remark;
	private Integer item_cnt;
	private Integer item_prc;
	private String item_comment;
	private String item_name;
	private String item_description;
	private byte[] item_pic;
	private String type;
	
	public BuyerOrderVO() {
	}

	public String getItem_comment() {
		return item_comment;
	}

	public void setItem_comment(String item_comment) {
		this.item_comment = item_comment;
	}

	public String getItem_no() {
		return item_no;
	}

	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}

	public Timestamp getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Timestamp order_time) {
		this.order_time = order_time;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Integer getOrder_prc() {
		return order_prc;
	}

	public void setOrder_prc(Integer order_prc) {
		this.order_prc = order_prc;
	}

	public String getOrder_trans() {
		return order_trans;
	}

	public void setOrder_trans(String order_trans) {
		this.order_trans = order_trans;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public Integer getItem_cnt() {
		return item_cnt;
	}

	public void setItem_cnt(Integer item_cnt) {
		this.item_cnt = item_cnt;
	}

	public Integer getItem_prc() {
		return item_prc;
	}

	public void setItem_prc(Integer item_prc) {
		this.item_prc = item_prc;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}


	public byte[] getItem_pic() {
		return item_pic;
	}

	public void setItem_pic(byte[] item_pic) {
		this.item_pic = item_pic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder_remark() {
		return order_remark;
	}

	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}

	public String getBuyer_no() {
		return buyer_no;
	}

	public void setBuyer_no(String buyer_no) {
		this.buyer_no = buyer_no;
	}
	
	
	
	
	
	
}

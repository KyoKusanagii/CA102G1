package com.mallorder.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MallOrderVO implements Serializable {
	
	private String mall_order_no;
	private String mall_buyer_no;
	private Timestamp mall_order_time;
	private Integer mall_order_prc;
	private String mall_order_trans	;
	private String mall_order_status;	
	private String mall_pay_status;		
	private String mall_order_remark;
	private String mall_transport;
	
	public MallOrderVO() {
		
	}
	
	public MallOrderVO(String mall_order_no, String mall_buyer_no, Timestamp mall_order_time, Integer mall_order_prc,
			String mall_order_trans, String mall_order_status, String mall_pay_status, String mall_order_remark) {
		this.mall_order_no = mall_order_no;
		this.mall_buyer_no = mall_buyer_no;
		this.mall_order_time = mall_order_time;
		this.mall_order_prc = mall_order_prc;
		this.mall_order_trans = mall_order_trans;
		this.mall_order_status = mall_order_status;
		this.mall_pay_status = mall_pay_status;
		this.mall_order_remark = mall_order_remark;
	}
	
	public String getMall_transport() {
		return mall_transport;
	}

	public void setMall_transport(String mall_transport) {
		this.mall_transport = mall_transport;
	}

	public String getMall_order_no() {
		return mall_order_no;
	}
	public void setMall_order_no(String mall_order_no) {
		this.mall_order_no = mall_order_no;
	}
	public String getMall_buyer_no() {
		return mall_buyer_no;
	}
	public void setMall_buyer_no(String mall_buyer_no) {
		this.mall_buyer_no = mall_buyer_no;
	}
	public Timestamp getMall_order_time() {
		return mall_order_time;
	}
	public void setMall_order_time(Timestamp mall_order_time) {
		this.mall_order_time = mall_order_time;
	}
	public Integer getMall_order_prc() {
		return mall_order_prc;
	}
	public void setMall_order_prc(Integer mall_order_prc) {
		this.mall_order_prc = mall_order_prc;
	}
	public String getMall_order_trans() {
		return mall_order_trans;
	}
	public void setMall_order_trans(String mall_order_trans) {
		this.mall_order_trans = mall_order_trans;
	}
	public String getMall_order_status() {
		return mall_order_status;
	}
	public void setMall_order_status(String mall_order_status) {
		this.mall_order_status = mall_order_status;
	}
	public String getMall_pay_status() {
		return mall_pay_status;
	}
	public void setMall_pay_status(String mall_pay_status) {
		this.mall_pay_status = mall_pay_status;
	}
	public String getMall_order_remark() {
		return mall_order_remark;
	}
	public void setMall_order_remark(String mall_order_remark) {
		this.mall_order_remark = mall_order_remark;
	}
	
	

}

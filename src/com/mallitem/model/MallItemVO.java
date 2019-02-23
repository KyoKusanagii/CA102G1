package com.mallitem.model;

import java.io.Serializable;

public class MallItemVO implements Serializable {
	
	private String mall_order_no;
	private String item_no;
	private Integer is_item_sale;
	private Integer mall_item_cnt;
	private Integer mall_item_prc;
	
	public MallItemVO() {
		
	}
	
	public MallItemVO(String mall_order_no, String item_no, Integer is_item_sale, Integer mall_item_cnt,
			Integer mall_item_prc) {
		this.mall_order_no = mall_order_no;
		this.item_no = item_no;
		this.is_item_sale = is_item_sale;
		this.mall_item_cnt = mall_item_cnt;
		this.mall_item_prc = mall_item_prc;
	}
	public String getMall_order_no() {
		return mall_order_no;
	}
	public void setMall_order_no(String mall_order_no) {
		this.mall_order_no = mall_order_no;
	}
	public String getItem_no() {
		return item_no;
	}
	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}
	public Integer getIs_item_sale() {
		return is_item_sale;
	}
	public void setIs_item_sale(Integer is_item_sale) {
		this.is_item_sale = is_item_sale;
	}
	public Integer getMall_item_cnt() {
		return mall_item_cnt;
	}
	public void setMall_item_cnt(Integer mall_item_cnt) {
		this.mall_item_cnt = mall_item_cnt;
	}
	public Integer getMall_item_prc() {
		return mall_item_prc;
	}
	public void setMall_item_prc(Integer mall_item_prc) {
		this.mall_item_prc = mall_item_prc;
	}
	
	

}

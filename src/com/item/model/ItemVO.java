package com.item.model;

import java.io.Serializable;

public class ItemVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String item_no;
	private String item_name;
	private Integer item_price;
	private Integer item_primary_class;
	private Integer item_secondary_class;
	private String item_owner;
	private Integer is_fb_launch;
	private Integer is_mall_launch;
	private Integer item_inventory;
	private String item_description;
	private String item_pic_no;
	private byte[] item_pic;
	private String encoded;
	
	//ÁÊª«¨®¥Î
	private Integer quantity;
	private Integer price;


	public String getItem_no() {
		return item_no;
	}

	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Integer getItem_price() {
		return item_price;
	}

	public void setItem_price(Integer item_price) {
		this.item_price = item_price;
	}

	public Integer getItem_primary_class() {
		return item_primary_class;
	}

	public void setItem_primary_class(Integer item_primary_class) {
		this.item_primary_class = item_primary_class;
	}

	public Integer getItem_secondary_class() {
		return item_secondary_class;
	}

	public void setItem_secondary_class(Integer item_secondary_class) {
		this.item_secondary_class = item_secondary_class;
	}

	public String getItem_owner() {
		return item_owner;
	}

	public void setItem_owner(String item_owner) {
		this.item_owner = item_owner;
	}

	public Integer getIs_fb_launch() {
		return is_fb_launch;
	}

	public void setIs_fb_launch(Integer is_fb_launch) {
		this.is_fb_launch = is_fb_launch;
	}

	public Integer getIs_mall_launch() {
		return is_mall_launch;
	}

	public void setIs_mall_launch(Integer is_mall_launch) {
		this.is_mall_launch = is_mall_launch;
	}

	public Integer getItem_inventory() {
		return item_inventory;
	}

	public void setItem_inventory(Integer item_inventory) {
		this.item_inventory = item_inventory;
	}

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public String getItem_pic_no() {
		return item_pic_no;
	}

	public void setItem_pic_no(String item_pic_no) {
		this.item_pic_no = item_pic_no;
	}

	public byte[] getItem_pic() {
		return item_pic;
	}

	public void setItem_pic(byte[] item_pic) {
		this.item_pic = item_pic;
	}

	public String getEncoded() {
		return encoded;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item_no == null) ? 0 : item_no.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVO other = (ItemVO) obj;
		if (item_no == null) {
			if (other.item_no != null)
				return false;
		} else if (!item_no.equals(other.item_no))
			return false;
		return true;
	}
}

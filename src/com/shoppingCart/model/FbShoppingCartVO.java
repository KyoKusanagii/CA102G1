package com.shoppingCart.model;

public class FbShoppingCartVO {

	private Integer quantity;
	private String fb_buyer;
	private String fb_buyer_no;
	private String fb_item_no;
	private String fb_comment;
	private Integer fb_item_price;
	private String fb_item_pic;

	public FbShoppingCartVO() {

	}

	public FbShoppingCartVO(Integer quantity, String fb_buyer, String fb_buyer_no, String fb_item_no, String fb_comment,
			Integer fb_item_price) {
		super();
		this.quantity = quantity;
		this.fb_buyer = fb_buyer;
		this.fb_buyer_no = fb_buyer_no;
		this.fb_item_no = fb_item_no;
		this.fb_comment = fb_comment;
		this.fb_item_price = fb_item_price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getFb_buyer() {
		return fb_buyer;
	}

	public void setFb_buyer(String fb_buyer) {
		this.fb_buyer = fb_buyer;
	}

	public String getFb_buyer_no() {
		return fb_buyer_no;
	}

	public void setFb_buyer_no(String fb_buyer_no) {
		this.fb_buyer_no = fb_buyer_no;
	}

	public String getFb_item_no() {
		return fb_item_no;
	}

	public void setFb_item_no(String fb_item_no) {
		this.fb_item_no = fb_item_no;
	}

	public String getFb_comment() {
		return fb_comment;
	}

	public void setFb_comment(String fb_comment) {
		this.fb_comment = fb_comment;
	}

	public Integer getFb_item_price() {
		return fb_item_price;
	}

	public void setFb_item_price(Integer fb_item_price) {
		this.fb_item_price = fb_item_price;
	}

	public String getFb_item_pic() {
		return fb_item_pic;
	}

	public void setFb_item_pic(String fb_item_pic) {
		this.fb_item_pic = fb_item_pic;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fb_buyer_no == null) ? 0 : fb_buyer_no.hashCode());
		result = prime * result + ((fb_item_no == null) ? 0 : fb_item_no.hashCode());
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
		FbShoppingCartVO other = (FbShoppingCartVO) obj;
		if (fb_buyer_no == null) {
			if (other.fb_buyer_no != null)
				return false;
		} else if (!fb_buyer_no.equals(other.fb_buyer_no))
			return false;
		if (fb_item_no == null) {
			if (other.fb_item_no != null)
				return false;
		} else if (!fb_item_no.equals(other.fb_item_no))
			return false;
		return true;
	}
	
	

}

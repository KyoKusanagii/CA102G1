package com.limitSaleSub.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Base64;

@SuppressWarnings("serial")
public class LimitSaleSubVO implements Serializable {
	private Integer sale_no;
	private String item_no;
	private Timestamp sale_start;
	private Timestamp sale_end;
	private Integer sale_price;
	private Integer sale_status;
	private String sale_remark;
	//以下非原生limitSale表格欄位, join item and itempic後出現
	private byte[] item_pic;
	private String item_owner;
	private String item_name;
	private Integer item_price;
	
	public byte[] getItem_pic() {
		return item_pic;
	}
	public void setItem_pic(byte[] item_pic) {
		this.item_pic = item_pic;
	}
	public String getItem_owner() {
		return item_owner;
	}
	public void setItem_owner(String seller_id) {
		this.item_owner = seller_id;
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
	public Integer getSale_no() {
		return sale_no;
	}
	public void setSale_no(Integer sale_no) {
		this.sale_no = sale_no;
	}
	public String getItem_no() {
		return item_no;
	}
	public void setItem_no(String item_no) {
		this.item_no = item_no;
	}
	public Timestamp getSale_start() {
		return sale_start;
	}
	public void setSale_start(Timestamp sale_start) {
		this.sale_start = sale_start;
	}
	public Timestamp getSale_end() {
		return sale_end;
	}
	public void setSale_end(Timestamp sale_end) {
		this.sale_end = sale_end;
	}
	public Integer getSale_price() {
		return sale_price;
	}
	public void setSale_price(Integer sale_price) {
		this.sale_price = sale_price;
	}
	public Integer getSale_status() {
		return sale_status;
	}
	public void setSale_status(Integer sale_status) {
		this.sale_status = sale_status;
	}
	public String getSale_remark() {
		return sale_remark;
	}
	public void setSale_remark(String sale_remark) {
		this.sale_remark = sale_remark;
	}
	
	public String getByteString() throws Exception {
		if (item_pic != null) {
			byte[] encodeBase64 = Base64.getEncoder().encode(item_pic);
			String base64DataString = new String(encodeBase64 , "UTF-8");
			return base64DataString;
		}
		else {
			return null;
		}
		
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		result = result + Integer.parseInt(item_no.substring(1));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {  
		
	    if(!(obj instanceof LimitSaleSubVO)  ) {
	    	return false;
	    }else {
	    	LimitSaleSubVO lsVO = (LimitSaleSubVO) obj;
	 	    if(this.getItem_no().equals(lsVO.getItem_no())) {
	 	    	return true;
	 	    }else {
	 	    	return false;
	 	    }
	    }
	} 
	
	
}

package com.limitSale.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class LimitSaleVO implements Serializable {
	private Integer sale_no;
	private String item_no;
	private Timestamp sale_start;
	private Timestamp sale_end;
	private Integer sale_price;
	private Integer sale_status;
	private String sale_remark;

	public LimitSaleVO() {

	}

	public LimitSaleVO(Integer sale_no, String item_no, Timestamp sale_start, Timestamp sale_end, Integer sale_price,
			Integer sale_status, String sale_remark) {
		super();
		this.sale_no = sale_no;
		this.item_no = item_no;
		this.sale_start = sale_start;
		this.sale_end = sale_end;
		this.sale_price = sale_price;
		this.sale_status = sale_status;
		this.sale_remark = sale_remark;
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

}

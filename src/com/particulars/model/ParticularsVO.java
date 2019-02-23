package com.particulars.model;

public class ParticularsVO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer cat_no;
	private Integer part_no;
	private String part_name;
	
	public Integer getCat_no() {
		return cat_no;
	}
	public void setCat_no(Integer cat_no) {
		this.cat_no = cat_no;
	}
	public Integer getPart_no() {
		return part_no;
	}
	public void setPart_no(Integer part_no) {
		this.part_no = part_no;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	@Override
	public String toString() {
		return "ParticularsVO [cat_no=" + cat_no + ", part_no=" + part_no + ", part_name=" + part_name + "]";
	}

}

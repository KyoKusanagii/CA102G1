package com.itempic.model;

public class ItempicVO implements java.io.Serializable {
	private String item_pic_no;
	private String item_no;
	private byte[] item_pic;
	private String encoded;

	public ItempicVO() {

	}

	public ItempicVO(String item_pic_no, String item_no, byte[] item_pic, String encoded) {
		super();
		this.item_pic_no = item_pic_no;
		this.item_no = item_no;
		this.item_pic = item_pic;
		this.encoded = encoded;
	}

	public String getItem_pic_no() {
		return item_pic_no;
	}

	public void setItem_pic_no(String item_pic_no) {
		this.item_pic_no = item_pic_no;
	}

	public String getItem_no() {
		return item_no;
	}

	public void setItem_no(String item_no) {
		this.item_no = item_no;
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

}

package com.carousel.model;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@SuppressWarnings("serial")
public class CarouselVO implements Serializable {
	private Integer carousel_no;
	private byte[] carousel_pic;
	private String carousel_title;
	private String carousel_subTitle;
	
	public Integer getCarousel_no() {
		return carousel_no;
	}
	public void setCarousel_no(Integer carousel_no) {
		this.carousel_no = carousel_no;
	}
	public byte[] getCarousel_pic() {
		if (carousel_pic != null) {
			return carousel_pic;
		}else {
			return null;
		}
	}
	public void setCarousel_pic(byte[] carousel_pic) {
		this.carousel_pic = carousel_pic;
	}
	public String getCarousel_title() {
		return carousel_title;
	}
	public void setCarousel_title(String carousel_title) {
		this.carousel_title = carousel_title;
	}
	public String getCarousel_subTitle() {
		return carousel_subTitle;
	}
	public void setCarousel_subTitle(String carousel_subTitle) {
		this.carousel_subTitle = carousel_subTitle;
	}
	
	public String getByteString() throws Exception {
		if (carousel_pic != null) {
			byte[] encodeBase64 = Base64.getEncoder().encode(carousel_pic);
		String base64DataString = new String(encodeBase64 , "UTF-8");
		return base64DataString;
		}
		else {
			return null;
		}
		
	}
	
}

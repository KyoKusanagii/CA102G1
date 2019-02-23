package com.specialForU.model;


import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class SpecialForUVO implements Serializable {
	private String mem_no;
	private Integer cloth_cnts;
	private Integer food_cnts;
	private Integer game_cnts;
	private Integer outdoor_cnts;
	private Integer homeelec_cnts;
	private Integer threec_cnts;
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public Integer getCloth_cnts() {
		return cloth_cnts;
	}
	public void setCloth_cnts(Integer cloth_cnts) {
		this.cloth_cnts = cloth_cnts;
	}
	public Integer getFood_cnts() {
		return food_cnts;
	}
	public void setFood_cnts(Integer food_cnts) {
		this.food_cnts = food_cnts;
	}
	public Integer getGame_cnts() {
		return game_cnts;
	}
	public void setGame_cnts(Integer game_cnts) {
		this.game_cnts = game_cnts;
	}
	public Integer getOutdoor_cnts() {
		return outdoor_cnts;
	}
	public void setOutdoor_cnts(Integer outdoor_cnts) {
		this.outdoor_cnts = outdoor_cnts;
	}
	public Integer getHomeelec_cnts() {
		return homeelec_cnts;
	}
	public void setHomeelec_cnts(Integer homeelec_cnts) {
		this.homeelec_cnts = homeelec_cnts;
	}
	public Integer getThreec_cnts() {
		return threec_cnts;
	}
	public void setThreec_cnts(Integer threec_cnts) {
		this.threec_cnts = threec_cnts;
	}


	
	
	
}

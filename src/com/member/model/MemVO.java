package com.member.model;

import java.io.Serializable;
import java.sql.Date;

public class MemVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String mem_no;
	private String mem_id;
	private String mem_psw;
	private String mem_email;
	private String mem_fbid;
	private String mem_name;
	private String mem_sex;
	private Date mem_birth;
	private String mem_mobile;
	private String mem_post;
	private String mem_address;
	private String mem_receiveadd;
	private Integer mem_condition;
	private Integer mem_artauth;
	private String mem_martinfo;
	private String mem_recommend;
	private byte[] mem_profilepic;
	private byte[] mem_martcover;
	private String profilepicEncoded;
	private String martcoverEncoded;
	private String mem_martname;
	private String mem_activecode;
	
	public String getMem_martname() {
		return mem_martname;
	}
	public void setMem_martname(String mem_martname) {
		this.mem_martname = mem_martname;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_psw() {
		return mem_psw;
	}
	public void setMem_psw(String mem_psw) {
		this.mem_psw = mem_psw;
	}
	
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	
	public String getMem_fbid() {
		return mem_fbid;
	}
	public void setMem_fbid(String mem_fbid) {
		this.mem_fbid = mem_fbid;
	}
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	
	public String getMem_sex() {
		return mem_sex;
	}
	public void setMem_sex(String mem_sex) {
		this.mem_sex = mem_sex;
	}
	
	public Date getMem_birth() {
		return mem_birth;
	}
	public void setMem_birth(Date mem_birth) {
		this.mem_birth = mem_birth;
	}
	
	public String getMem_mobile() {
		return mem_mobile;
	}
	public void setMem_mobile(String mem_mobile) {
		this.mem_mobile = mem_mobile;
	}
	
	public String getMem_post() {
		return mem_post;
	}
	public void setMem_post(String mem_post) {
		this.mem_post = mem_post;
	}
	
	public String getMem_address() {
		return mem_address;
	}
	public void setMem_address(String mem_address) {
		this.mem_address = mem_address;
	}
	
	public String getMem_receiveadd() {
		return mem_receiveadd;
	}
	public void setMem_receiveadd(String mem_receiveadd) {
		this.mem_receiveadd = mem_receiveadd;
	}
	
	public Integer getMem_condition() {
		return mem_condition;
	}
	public void setMem_condition(Integer mem_condition) {
		this.mem_condition = mem_condition;
	}
	
	public Integer getMem_artauth() {
		return mem_artauth;
	}
	public void setMem_artauth(Integer mem_artauth) {
		this.mem_artauth = mem_artauth;
	}
	
	public String getMem_martinfo() {
		return mem_martinfo;
	}
	public void setMem_martinfo(String mem_martinfo) {
		this.mem_martinfo = mem_martinfo;
	}
	
	public String getMem_recommend() {
		return mem_recommend;
	}
	public void setMem_recommend(String mem_recommend) {
		this.mem_recommend = mem_recommend;
	}
	
	public byte[] getMem_profilepic() {
		return mem_profilepic;
	}
	public void setMem_profilepic(byte[] mem_profilepic) {
		this.mem_profilepic = mem_profilepic;
	}
	
	public byte[] getMem_martcover() {
		return mem_martcover;
	}
	public void setMem_martcover(byte[] mem_martcover) {
		this.mem_martcover = mem_martcover;
	}
	
	public String getProfilepicEncoded() {
		return profilepicEncoded;
	}
	public void setProfilepicEncoded(String profilepicEncoded) {
		this.profilepicEncoded = profilepicEncoded;
	}
	
	public String getMartcoverEncoded() {
		return martcoverEncoded;
	}
	public void setMartcoverEncoded(String martcoverEncoded) {
		this.martcoverEncoded = martcoverEncoded;
	}
	
	
	public String getMem_activecode() {
		return mem_activecode;
	}
	public void setMem_activecode(String mem_activecode) {
		this.mem_activecode = mem_activecode;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mem_no == null) ? 0 : mem_no.hashCode());
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
		MemVO other = (MemVO) obj;
		if (mem_no == null) {
			if (other.mem_no != null)
				return false;
		} else if (!mem_no.equals(other.mem_no))
			return false;
		return true;
	}

}

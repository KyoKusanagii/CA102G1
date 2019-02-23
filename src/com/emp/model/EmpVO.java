package com.emp.model;

import java.io.Serializable;

public class EmpVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String emp_no;
	private String emp_id;
	private String emp_pwd;
	private String emp_name;
	private byte[] emp_icon;
	private Integer emp_status;
	private Integer emp_mem_auth;
	private Integer emp_carousel_auth;
	private Integer emp_report_auth;
	private Integer emp_chat_auth;
	private Integer emp_level;	//後臺管理者or一般員工
	
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_pwd() {
		return emp_pwd;
	}
	public void setEmp_pwd(String emp_pwd) {
		this.emp_pwd = emp_pwd;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public byte[] getEmp_icon() {
		return emp_icon;
	}
	public void setEmp_icon(byte[] emp_icon) {
		this.emp_icon = emp_icon;
	}
	public int getEmp_level() {
		return emp_level;
	}
	public void setEmp_level(int emp_level) {
		this.emp_level = emp_level;
	}
	public int getEmp_mem_auth() {
		return emp_mem_auth;
	}
	public void setEmp_mem_auth(int emp_mem_auth) {
		this.emp_mem_auth = emp_mem_auth;
	}
	public int getEmp_carousel_auth() {
		return emp_carousel_auth;
	}
	public void setEmp_carousel_auth(int emp_carousel_auth) {
		this.emp_carousel_auth = emp_carousel_auth;
	}
	public int getEmp_report_auth() {
		return emp_report_auth;
	}
	public void setEmp_report_auth(int emp_report_auth) {
		this.emp_report_auth = emp_report_auth;
	}
	public int getEmp_chat_auth() {
		return emp_chat_auth;
	}
	public void setEmp_chat_auth(int emp_chat_auth) {
		this.emp_chat_auth = emp_chat_auth;
	}
	public int getEmp_status() {
		return emp_status;
	}
	public void setEmp_status(int emp_status) {
		this.emp_status = emp_status;
	}

}

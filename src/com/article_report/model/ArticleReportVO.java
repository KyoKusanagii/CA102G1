package com.article_report.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ArticleReportVO implements Serializable{
	
	private String article_report_no;
	private String mem_no;		//有Timestamp類型的格式
	private String arti_no;
	private String report_description;
	private String emp_no;
	private String report_reasons;
	
	public String getReport_reasons() {
		return report_reasons;
	}
	public void setReport_reasons(String report_reasons) {
		this.report_reasons = report_reasons;
	}
	private Integer report_status;
	
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	
	public String getArticle_report_no() {
		return article_report_no;
	}
	public void setArticle_report_no(String article_report_no) {
		this.article_report_no = article_report_no;
	}
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getArti_no() {
		return arti_no;
	}
	public void setArti_no(String arti_no) {
		this.arti_no = arti_no;
	}
	public String getReport_description() {
		return report_description;
	}
	public void setReport_description(String report_description) {
		this.report_description = report_description;
	}
	public Integer getReport_status() {
		return report_status;
	}
	public void setReport_status(Integer report_status) {
		this.report_status = report_status;
	}
	
	
	
	
}

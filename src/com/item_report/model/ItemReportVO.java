package com.item_report.model;

import java.io.Serializable;

public class ItemReportVO implements Serializable{
	private String item_Report_NO;
	private String mem_NO;
	private String emp_NO;
	private String item_NO;
	private String report_reasons;
	private byte[] reportPic;
	
	
	public byte[] getReportPic() {
		return reportPic;
	}
	public void setReportPic(byte[] reportPic) {
		this.reportPic = reportPic;
	}
	public String getReport_reasons() {
		return report_reasons;
	}
	public void setReport_reasons(String report_reasons) {
		this.report_reasons = report_reasons;
	}
	private String report_Description;
	private Integer report_Status;
	
	public String getItem_Report_NO() {
		return item_Report_NO;
	}
	public void setItem_Report_NO(String item_Report_NO) {
		this.item_Report_NO = item_Report_NO;
	}
	public String getMem_NO() {
		return mem_NO;
	}
	public void setMem_NO(String mem_NO) {
		this.mem_NO = mem_NO;
	}
	public String getEmp_NO() {
		return emp_NO;
	}
	public void setEmp_NO(String emp_NO) {
		this.emp_NO = emp_NO;
	}
	public String getItem_NO() {
		return item_NO;
	}
	public void setItem_NO(String item_NO) {
		this.item_NO = item_NO;
	}
	public String getReport_Description() {
		return report_Description;
	}
	public void setReport_Description(String report_Description) {
		this.report_Description = report_Description;
	}
	public Integer getReport_Status() {
		return report_Status;
	}
	public void setReport_Status(Integer report_Status) {
		this.report_Status = report_Status;
	}	
	
}

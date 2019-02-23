package com.artireply_report.model;

import java.util.List;

public interface ArtiReplyReportDAO_interface {
	public void insert(ArtiReplyReportVO artiReplyReportVO);
	public void update(String artiReply_report_no,int artiReply_report_status);
	public void delete(Integer artiReplyReportRepNo);
	public void delete(String arti_no);
	ArtiReplyReportVO findByPrimaryKey(String artiReplyReportNO);
	public List<ArtiReplyReportVO> getAll();
}

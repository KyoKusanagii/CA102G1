package com.artireply_report.model;

import java.util.List;

public class ArtiReplyReportService {
	
	private ArtiReplyReportDAO_interface dao;
	
	
	
	public ArtiReplyReportService() {
		
		dao = new ArtiReplyReportDAO();
	}
	
	public void addArtiReplyReport(String mem_no,String arti_no,Integer rep_no,
			String report_description,Integer report_status,String report_reasons) {
			
		ArtiReplyReportVO artiReplyReport = new ArtiReplyReportVO();
		artiReplyReport.setMem_NO(mem_no);
		artiReplyReport.setArti_NO(arti_no);
		artiReplyReport.setRep_NO(rep_no);
		artiReplyReport.setReport_Description(report_description);
		artiReplyReport.setReport_Status(report_status);
		artiReplyReport.setReport_Reasons(report_reasons);
		dao.insert(artiReplyReport);
	}
	public void deleteArtiReplyReport(Integer artiReplyReportRepNo) {
		dao.delete(artiReplyReportRepNo);
	}
	
	public void	deleteArtiReplyReportAll(String arti_no) {
		dao.delete(arti_no);
	}
	
	public void updateArtiReplyReport(String artiReply_report_no,int artiReply_report_status) {
		dao.update(artiReply_report_no, artiReply_report_status);
	}

	public List<ArtiReplyReportVO> getAllArtiReply(){
		
		List<ArtiReplyReportVO> artiReplyReportList = dao.getAll();
		
		return artiReplyReportList;
		
	}
}

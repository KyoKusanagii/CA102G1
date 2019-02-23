package com.article_report.model;

import java.util.List;

public class ArticleReportService {
	
	private Article_reportDAO_interface dao;
	
	public ArticleReportService() {	
		dao = new ArticleReportDAO();		
	}
	
	public void addArticleReport(String mem_no,String arti_no,Integer report_status,
			String report_reasons,String report_description) {
		ArticleReportVO articleReport = new ArticleReportVO();
		articleReport.setMem_no(mem_no);
		articleReport.setArti_no(arti_no);
		articleReport.setReport_status(report_status);
		articleReport.setReport_reasons(report_reasons);
		articleReport.setReport_description(report_description);
		dao.insert(articleReport);
	}
	
	public void updateArticleReport(String article_report_no,int article_report_status) {
		dao.update(article_report_no, article_report_status);
		
	}
	
	public void deleteArticleReport(String arti_no) {
		dao.delete(arti_no);
	}
	
	public ArticleReportVO findArticleReport(String article_report_no) {
		ArticleReportVO articleReport = dao.findByPK(article_report_no);
		
		return articleReport;
	}
	
	public List<ArticleReportVO> getAllAritcleReport(){
		List<ArticleReportVO> articleReportList = dao.getAll();	
		return articleReportList;
	}
}

package com.article_report.model;

import java.util.List;

public interface Article_reportDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(ArticleReportVO article_report);
	void update(String article_report_no,int article_report_status);
	void delete(String arti_no);
	
	ArticleReportVO findByPK(String article_report_no);
	List<ArticleReportVO > getAll();

}
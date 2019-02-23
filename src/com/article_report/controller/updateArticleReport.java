package com.article_report.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportService;
import com.article_report.model.ArticleReportVO;
import com.emp.model.EmpDAO;
import com.emp.model.EmpVO;

@WebServlet("/updateArticleReport")
public class updateArticleReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
  

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ArticleReportService service = new ArticleReportService();
		String articleReportStatus = req.getParameter("articleReportStatus");
		String articleReportNo = req.getParameter("articleReportNo");
		service.updateArticleReport(articleReportNo,Integer.parseInt(articleReportStatus));
		
	}

}

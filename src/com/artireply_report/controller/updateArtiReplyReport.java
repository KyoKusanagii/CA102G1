package com.artireply_report.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportVO;
import com.artireply_report.model.ArtiReplyReportDAO;
import com.artireply_report.model.ArtiReplyReportService;
import com.artireply_report.model.ArtiReplyReportVO;
import com.emp.model.EmpDAO;
import com.emp.model.EmpVO;

@WebServlet("/updateArtiReplyReport")
public class updateArtiReplyReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		ArtiReplyReportService service = new ArtiReplyReportService();
		String artiReplyReportStatus = req.getParameter("artiReplyReportStatus");
		String artiReplyReportNo = req.getParameter("artiReplyReportNo");
		service.updateArtiReplyReport(artiReplyReportNo,Integer.parseInt(artiReplyReportStatus));
		
	}

}

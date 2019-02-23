package com.artireply_report.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.artireply_report.model.ArtiReplyReportService;

@WebServlet("/addArtiReplyReport")
public class addArtiReplyReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		
		System.out.println("檢舉留言囉!!");
	
		ArtiReplyReportService service = new ArtiReplyReportService();
		String artiReplyReportDescription = req.getParameter("artiReplyReportDescription");
		String artiReplyNo = req.getParameter("artiReplyNo");
		String artiReplyReporter = req.getParameter("artiReplyReporter");
		String articleNo = req.getParameter("articleNo");
		String artiReplyReasons = req.getParameter("artiReplyReasons");
		System.out.println("檢舉留言原因"+artiReplyReasons);
		service.addArtiReplyReport(artiReplyReporter, articleNo, Integer.parseInt(artiReplyNo), 
				artiReplyReportDescription, 0, artiReplyReasons);
		
	}

}

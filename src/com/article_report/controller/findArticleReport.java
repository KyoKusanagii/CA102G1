package com.article_report.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportService;
import com.article_report.model.ArticleReportVO;

@WebServlet("/findArticleReport")
public class findArticleReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		ArticleReportService service = new ArticleReportService();
		String sendArticleReportNo = req.getParameter("sendArticleReportNo"); //得到傳送過來的文章檢舉編號
		ArticleReportVO anArticleReport = service.findArticleReport(sendArticleReportNo);	//得到一筆查到的文章資訊	
		System.out.println("要查詢的文章檢舉編號為:"+sendArticleReportNo);
		System.out.println("回傳的檢舉文章:"+anArticleReport);
		req.setAttribute("anArticleReport",anArticleReport);
//		res.sendRedirect(req.getContextPath()+"/BackEnd/ForumReport.jsp");
		RequestDispatcher rd = req.getRequestDispatcher("/BackEnd/ForumReport.jsp");
		rd.forward(req, res);		
	}

}

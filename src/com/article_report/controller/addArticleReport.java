package com.article_report.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ecs.xhtml.html;

import com.article_report.model.ArticleReportService;

@WebServlet("/addArticleReport")
public class addArticleReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession();
		try {                                                        
			String location = (String) session.getAttribute("location");
			if (location != null) {
				session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
				res.sendRedirect(location);   
				return;
			}
		}catch (Exception ignored) {
		}
		System.out.println("檢舉文章囉!!");
		ArticleReportService service = new ArticleReportService();
		String articleReportDescription = req.getParameter("articleReportDescription");
		String articleNo = req.getParameter("articleNo");
		String articleReporter = req.getParameter("articleReporter");
		String articleReasons = req.getParameter("articleReasons");
		System.out.println("文章檢舉原因:"+articleReasons);
		service.addArticleReport(articleReporter, articleNo, 0, articleReasons, articleReportDescription);
	}

}

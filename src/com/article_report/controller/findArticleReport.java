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
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		ArticleReportService service = new ArticleReportService();
		String sendArticleReportNo = req.getParameter("sendArticleReportNo"); //�o��ǰe�L�Ӫ��峹���|�s��
		ArticleReportVO anArticleReport = service.findArticleReport(sendArticleReportNo);	//�o��@���d�쪺�峹��T	
		System.out.println("�n�d�ߪ��峹���|�s����:"+sendArticleReportNo);
		System.out.println("�^�Ǫ����|�峹:"+anArticleReport);
		req.setAttribute("anArticleReport",anArticleReport);
//		res.sendRedirect(req.getContextPath()+"/BackEnd/ForumReport.jsp");
		RequestDispatcher rd = req.getRequestDispatcher("/BackEnd/ForumReport.jsp");
		rd.forward(req, res);		
	}

}

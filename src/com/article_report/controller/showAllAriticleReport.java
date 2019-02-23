package com.article_report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.article.model.ArticleService;
import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportService;
import com.article_report.model.ArticleReportVO;
import com.artireply_report.model.ArtiReplyReportVO;
import com.member.model.MemService;

@WebServlet("/showAllArticleReport")
public class showAllAriticleReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		ArticleReportService service = new ArticleReportService();
		List<ArticleReportVO> articleReportList = service.getAllAritcleReport();
		PrintWriter out = res.getWriter();
		JSONObject json = null;
		JSONArray jarray = new JSONArray();
		MemService memService = new MemService();
		ArticleService articleService = new ArticleService();
		for(ArticleReportVO articleReport : articleReportList) {
			json = new JSONObject();
			try {
				json.put("article_report_no",articleReport.getArticle_report_no());
				json.put("mem_name",memService.findByPK(articleReport.getMem_no()).getMem_name());
				json.put("arti_topic", articleService.getOneArticle(articleReport.getArti_no()).getArti_topic());
				json.put("report_description", articleReport.getReport_description());
				json.put("report_status", articleReport.getReport_status());
				if(articleReport.getReport_reasons() == null)
					json.put("article_report_reasons", "");
				else if(articleReport.getReport_reasons() != null)
					json.put("article_report_reasons", articleReport.getReport_reasons());;
				//System.out.println(articleReport.getReport_reasons());
				jarray.put(json);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		out.println(jarray);
	}

}

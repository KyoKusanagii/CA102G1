package com.artireply_report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arti_reply.model.Arti_replyService;
import com.article.model.ArticleService;
import com.article_report.model.ArticleReportService;
import com.artireply_report.model.ArtiReplyReportDAO;
import com.artireply_report.model.ArtiReplyReportService;
import com.artireply_report.model.ArtiReplyReportVO;
import com.member.model.MemService;

@WebServlet("/showAllArtiReplyReport")
public class showAllArtiReplyReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		ArtiReplyReportService service = new ArtiReplyReportService();
		List<ArtiReplyReportVO> artiReplyReportList = service.getAllArtiReply();
		JSONObject json = null;
		JSONArray jarray = new JSONArray();
		MemService memService = new MemService();
		Arti_replyService artiService = new Arti_replyService();
		ArticleService articleService = new ArticleService();
		
		for(ArtiReplyReportVO artiReplyReport : artiReplyReportList) {
			json = new JSONObject();
			try {
				//System.out.println(articleService.getOneArticle(artiReplyReport.getArti_NO()).getArti_topic());
				json.put("artiReply_report_no",artiReplyReport.getArtiReply_Report_NO());
				json.put("mem_name", memService.findByPK(artiReplyReport.getMem_NO()).getMem_name());
				json.put("arti_topic", articleService.getOneArticle(artiReplyReport.getArti_NO()).getArti_topic());
				json.put("rep_content", artiService.getOneRep(artiReplyReport.getRep_NO()).getRep_content());
				json.put("report_description", artiReplyReport.getReport_Description());
				json.put("report_status", artiReplyReport.getReport_Status());	
				if(artiReplyReport.getReport_Reasons() != null)
					json.put("artiReply_report_reasons",artiReplyReport.getReport_Reasons());
				else if (artiReplyReport.getReport_Reasons() == null)
					json.put("artiReply_report_reasons", "");
				jarray.put(json);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		out.println(jarray);
	}

}

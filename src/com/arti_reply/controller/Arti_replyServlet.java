package com.arti_reply.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arti_reply.model.Arti_replyService;
import com.article.model.ArticleService;
import com.artireply_report.model.ArtiReplyReportService;

@WebServlet("/FrontEnd/forum/arti_reply.do")
public class Arti_replyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		if ("insert".equals(action)) { // 來自article.jsp的請求  
			   
			   Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			   req.setAttribute("errorMsgs", errorMsgs);

			   try {
			    /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			    String mem_no = req.getParameter("mem_no");
				String arti_no = req.getParameter("arti_no").trim();
				System.out.println(arti_no);
			    String rep_content = req.getParameter("rep_content");
			    if (rep_content == null || rep_content.trim().length() == 0) {
			     errorMsgs.put("rep_content","回覆內容請勿空白");
			    } 
			    
			    Timestamp rep_time = new Timestamp(System.currentTimeMillis());
			     
			
			
			    // Send the use back to the form, if there were errors
			    if (!errorMsgs.isEmpty()) {
			     RequestDispatcher failureView = req
			       .getRequestDispatcher("/FrontEnd/forum/article.jsp");
			     failureView.forward(req, res);
			     return;
			    }
			    
			    /***************************2.開始新增資料***************************************/
			    Arti_replyService arti_replySvc = new Arti_replyService();
			    arti_replySvc.addRep(mem_no,rep_content, rep_time, arti_no);
			    System.out.println("新增成功");
			    
			    /***************************3.新增完成,準備轉交(Send the Success view)***********/
			    String url = req.getContextPath() + "/FrontEnd/forum/article.jsp?arti_no=" + arti_no;
			    res.sendRedirect(url);
			    
			    /***************************其他可能的錯誤處理**********************************/
			   } catch (Exception e) {
			    errorMsgs.put("Exception",e.getMessage());
			    RequestDispatcher failureView = req
			      .getRequestDispatcher("/FrontEnd/forum/forum.jsp");
			    failureView.forward(req, res);
			   }
			}
		
		
		if ("delete".equals(action)) { // 來自article.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String arti_no = req.getParameter("arti_no");
				Integer rep_no =Integer.parseInt(req.getParameter("rep_no"));
				
				/***************************2.開始刪除資料***************************************/
				Arti_replyService arti_replySvc = new Arti_replyService();	
				ArtiReplyReportService artireply_reportSvc = new ArtiReplyReportService();
				arti_replySvc.deleteRep(arti_no, rep_no); //留言刪除
				artireply_reportSvc.deleteArtiReplyReport(rep_no);	//刪除留言時順便刪除討論區檢舉的檢舉留言
				System.out.println("刪除成功");
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = req.getContextPath() + "/FrontEnd/forum/article.jsp?arti_no=" + arti_no;
				res.sendRedirect(url);
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/FrontEnd/forum/forum.jsp");
				failureView.forward(req, res);
			}
		}
		
		
	}

}

package com.article.controller;

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
import com.article.model.ArticleVO;
import com.article_report.model.ArticleReportDAO;
import com.article_report.model.ArticleReportService;
import com.artireply_report.model.ArtiReplyReportService;




/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/FrontEnd/forum/article.do")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("findByTopic".equals(action)) { // 來自forum.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String arti_topic = req.getParameter("arti_topic");
				if (arti_topic == null || (arti_topic.trim()).length() == 0) {
					errorMsgs.add("請輸入要搜尋主題");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/forum/forum.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ArticleService articleSvc = new ArticleService();
				List<ArticleVO> list = articleSvc.getArticle(arti_topic);
				
//				if (list.isEmpty()) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/FrontEnd/forum/forum.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.getSession().setAttribute("list", list);
			
				String url = "/FrontEnd/forum/getArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 getArticle.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/FrontEnd/forum/forum.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("insert".equals(action)) { // 來自po_arti.jsp的請求  
			   
		   Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		   req.setAttribute("errorMsgs", errorMsgs);

		   try {
		    /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
		    
		    String arti_topic = req.getParameter("arti_topic");
		    if (arti_topic == null || arti_topic.trim().length() == 0) {
		     errorMsgs.put("arti_topic","標題請勿空白");
		    } 
		    
		    String arti_content = req.getParameter("arti_content");
		    if (arti_content == null || arti_content.trim().length() == 0) {
		     errorMsgs.put("arti_content","文章內容請勿空白");
		    } 
		    String mem_no = req.getParameter("mem_no");
		    Timestamp po_time = new Timestamp(System.currentTimeMillis());
		    
		   
		
		    // Send the use back to the form, if there were errors
		    if (!errorMsgs.isEmpty()) {
		    req.setAttribute("arti_topic", arti_topic);
		    req.setAttribute("arti_content", arti_content);
		     RequestDispatcher failureView = req
		       .getRequestDispatcher("/FrontEnd/forum/po_arti.jsp");
		     failureView.forward(req, res);
		     return;
		    }
		    
		    /***************************2.開始新增資料***************************************/
		    ArticleService articleSvc = new ArticleService();
		    articleSvc.addArticle(arti_topic,mem_no, arti_content, po_time);
		    System.out.println("新增成功");
		    /***************************3.新增完成,準備轉交(Send the Success view)***********/
		    String url = "/FrontEnd/forum/forum.jsp";
		    RequestDispatcher successView = req.getRequestDispatcher(url);
		    successView.forward(req, res);    
		    
		    /***************************其他可能的錯誤處理**********************************/
		   } catch (Exception e) {
		    errorMsgs.put("Exception",e.getMessage());
		    RequestDispatcher failureView = req
		      .getRequestDispatcher("/FrontEnd/forum/po_arti.jsp");
		    failureView.forward(req, res);
		   }
		}
		
		if ("update".equals(action)) { // 來自update_arti.jsp的請求
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String arti_no = req.getParameter("arti_no").trim();
				String arti_topic = req.getParameter("arti_topic");
				if (arti_topic == null || arti_topic.trim().length() == 0) {
					errorMsgs.put("arti_topic","標題請勿空白");
			    } 
				String arti_content = req.getParameter("arti_content");	
				if (arti_content == null || arti_content.trim().length() == 0) {
					errorMsgs.put("arti_content","文章內容請勿空白");
			    } 
				Timestamp po_time = new Timestamp(System.currentTimeMillis());
				
				

				ArticleVO articleVO = new ArticleVO();
				articleVO.setArti_topic(arti_topic);
				articleVO.setArti_content(arti_content);
				articleVO.setPo_time(po_time);
				articleVO.setArti_no(arti_no);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("articleVO", articleVO); // 含有輸入格式錯誤的articleVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/forum/update_arti.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				
				
				/***************************2.開始修改資料*****************************************/
				ArticleService articleSvc = new ArticleService();
				articleVO = articleSvc.updateArticle(arti_topic, arti_content, po_time, arti_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("articleVO", articleVO); // 資料庫update成功後,正確的的articleVO物件,存入req
				String url = "/FrontEnd/forum/article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交forum.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/FrontEnd/forum/update_arti.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // 來自article.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String arti_no = req.getParameter("arti_no");
				
				/***************************2.開始刪除資料***************************************/
				ArticleService articleSvc = new ArticleService();
				Arti_replyService arti_replySvc = new Arti_replyService();
				ArticleReportService arti_reportSvc = new ArticleReportService();
				ArtiReplyReportService arti_reply_reportSvc = new ArtiReplyReportService();
				arti_reply_reportSvc.deleteArtiReplyReportAll(arti_no);	//刪除檢舉文章下所有檢舉的留言
				arti_replySvc.deleteAllRep(arti_no); //先將文章內所有留言刪除
				arti_reportSvc.deleteArticleReport(arti_no);//刪除檢舉文章的文章
				articleSvc.deleteArticle(arti_no); //在刪除文章
				System.out.println("刪除成功");
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = req.getContextPath() + "/FrontEnd/forum/forum.jsp";
				res.sendRedirect(url);// 刪除成功後,轉交回送出刪除的來源網頁
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/FrontEnd/forum/po_arti.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
	}

}

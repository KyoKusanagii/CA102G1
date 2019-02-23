package com.news.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.member.model.MemService;
import com.member.model.MemVO;
import com.news.model.NewsService;
import com.news.model.NewsVO;

@WebServlet("/BackEnd/news.do")
public class NewsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("insert".equals(action)) { // 來自po_arti.jsp的請求  
			   
		   Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		   req.setAttribute("errorMsgs", errorMsgs);

		   try {
			    /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			    
			    String news_title = req.getParameter("news_title");
			    if (news_title == null || news_title.trim().length() == 0) {
			     errorMsgs.put("news_topic","標題: 請勿空白");
			    } 
			    
			    String news_content = req.getParameter("news_content");
			    if (news_content == null || news_content.trim().length() == 0) {
			     errorMsgs.put("news_content","文章內容: 請勿空白");
			    } 
			    
			    
			    String news_date_day = req.getParameter("news_date_day");
			    System.out.println(news_date_day);
			    String news_date_time = req.getParameter("news_date_time");
			    System.out.println(news_date_time);
			    String hr = news_date_time.substring(0,2);
			    String min = news_date_time.substring(5,7);
			    String sec = news_date_time.substring(10,12);
			    news_date_time = hr + ":" + min + ":" + sec;
			    System.out.println(news_date_time);
			    System.out.println(news_date_time.length());
			    Timestamp news_date = java.sql.Timestamp.valueOf(news_date_day + " " + news_date_time);
			    System.out.println(news_date);
			     
			
			    // Send the use back to the form, if there were errors
			    if (!errorMsgs.isEmpty()) {
			     RequestDispatcher failureView = req
			       .getRequestDispatcher("/BackEnd/news/NewsPost.jsp");
			     failureView.forward(req, res);
			     return;
			    }
			    
			    /***************************2.開始新增資料***************************************/
			    NewsService newsSvc = new NewsService();
			    newsSvc.insert(news_date, news_title, news_content);
			    System.out.println("新增成功");
			    /***************************3.新增完成,準備轉交(Send the Success view)***********/
			    String url = req.getContextPath() + "/BackEnd/news/News.jsp";
			    res.sendRedirect(url);
			    /***************************其他可能的錯誤處理**********************************/
			   } catch (Exception e) {
			    errorMsgs.put("Exception",e.getMessage());
			    RequestDispatcher failureView = req
			      .getRequestDispatcher("/BackEnd/news/NewsPost.jsp");
			    failureView.forward(req, res);
			   }
			}
		
		
		
		if ("update".equals(action)) { // 來自update_arti.jsp的請求
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String news_title = req.getParameter("news_title");
			    if (news_title == null || news_title.trim().length() == 0) {
			     errorMsgs.put("news_topic","標題: 請勿空白");
			    }
			    System.out.println("修改標題OK");
			    
			    String news_content = req.getParameter("news_content");
			    if (news_content == null || news_content.trim().length() == 0) {
			     errorMsgs.put("news_content","文章內容: 請勿空白");
			    } 
			    System.out.println("修改內容OK");
			    
			    String news_date_day = req.getParameter("news_date_day");
			    String news_date_time = req.getParameter("news_date_time");
			    String hr = news_date_time.substring(0,2);
			    String min = news_date_time.substring(5,7);
			    String sec = news_date_time.substring(10,12);
			    news_date_time = hr + ":" + min + ":" + sec;
			    Timestamp news_date = java.sql.Timestamp.valueOf(news_date_day + " " + news_date_time);
			    System.out.println("修改時間OK");

			    
			    Integer news_no = new Integer(req.getParameter("news_no"));
			    System.out.println("確認NEWS編號");
				
				

				NewsVO newsVO = new NewsVO();
				newsVO.setNews_date(news_date);
				newsVO.setNews_title(news_title);
				newsVO.setNews_content(news_content);
				newsVO.setNews_no(news_no);
				System.out.println("重新裝VO");
				
				
				/***************************2.開始修改資料*****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.update(news_no, news_date, news_title, news_content);
				System.out.println("修改OK");
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("newsVO", newsVO); // 資料庫update成功後,正確的的articleVO物件,存入req
				String url = "/BackEnd/news/News.jsp";
			    RequestDispatcher successView = req.getRequestDispatcher(url);
			    successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.put("修改資料失敗:",e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/BackEnd/news/News.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if ("delete".equals(action)) { // 來自article.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer news_no = new Integer(req.getParameter("news_no"));
				
				/***************************2.開始刪除資料***************************************/
				NewsService newsSvc = new NewsService();
				newsSvc.delete(news_no); //在刪除文章
				System.out.println("刪除成功");
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/BackEnd/news/News.jsp";
			    RequestDispatcher successView = req.getRequestDispatcher(url);
			    successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/BackEnd/news/News.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("findByPK".equals(action)) { // 來自article.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer news_no = new Integer(req.getParameter("news_no"));
				
				/***************************2.開始刪除資料***************************************/
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(news_no);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/BackEnd/news/NewsUpdate.jsp";
			    RequestDispatcher successView = req.getRequestDispatcher(url);
			    successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("修改失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/BackEnd/news/News.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}

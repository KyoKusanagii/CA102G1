package com.follow_mem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.follow_item.model.*;
import com.follow_mem.model.*;

/**
 * Servlet implementation class Follow_itemServlet
 */
@WebServlet("/follow.do")
public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("BIG5");
		String action = req.getParameter("action");
		

		
		/*************************** 關注商品  ********************************/
		  if ("folo_item".equals(action)) {
		   PrintWriter out = res.getWriter();
		 
		   try {

		    /*************************** 1.接收請求參數 ***************************************/

		    String mem_no = req.getParameter("mem_no");
		    if(mem_no == null || mem_no.trim().length() == 0) {
		    	
				return;
			}
		    String item_no = req.getParameter("item_no");
		    
		    Date folo_time = new Date(System.currentTimeMillis());
		    
		    System.out.println("mem_no = " + mem_no);
		    System.out.println("item_no = " + item_no);
		    

		    /******************************************************************/

		    Follow_itemService folo_itemSvc = new Follow_itemService();
		    Follow_itemVO cnt = folo_itemSvc.findByPK(mem_no, item_no);
		    //若尚未關注過顯示加入關注
		    if (cnt == null) {
		    	folo_itemSvc.add(mem_no, item_no, folo_time);
		    	System.out.println("加入成功");
		    } else {
		    //若已經關注則顯示取消關注	
		    	folo_itemSvc.delete(mem_no, item_no);
		    	System.out.println("刪除成功");
		    }

		    /*************************** 3.完成，準備轉交(Send the Success view) ***********/

		    // 此為AJAX寫法，不用轉交

		    /*************************** 其他可能的錯誤處理 **********************************/
		   } catch (Exception e) {
		    out.print("失敗");
		   }
		  }
		
		  
		  if ("folo_mem".equals(action)) {
			   PrintWriter out = res.getWriter();
			 
			   try {

			    /*************************** 1.接收請求參數 ***************************************/

			    String folo_mem_no = req.getParameter("folo_mem_no");
			    if(folo_mem_no == null || folo_mem_no.trim().length() == 0) {
					return;
				}
			    String foloed_mem_no = req.getParameter("foloed_mem_no");
			    
			    Date folo_time = new Date(System.currentTimeMillis());
			    
			    System.out.println("folo_mem_no = " + folo_mem_no);
			    System.out.println("foloed_mem_no = " + foloed_mem_no);
			    

			    /******************************************************************/
			
			    Follow_memService folo_memSvc = new Follow_memService();
			    Follow_memVO cnt = folo_memSvc.findByPK(folo_mem_no, foloed_mem_no);
			    //若尚未關注過顯示加入關注
			    if (cnt == null) {
			    	folo_memSvc.add(folo_mem_no, foloed_mem_no, folo_time);
			    	System.out.println("加入成功");
			    
			    	
			    } else {
			    //若已經關注則顯示取消關注	
			    	folo_memSvc.delete(folo_mem_no, foloed_mem_no);
			    	System.out.println("刪除成功");
			    	
			    }
			 

			    /*************************** 3.完成，準備轉交(Send the Success view) ***********/

			    // 此為AJAX寫法，不用轉交

			    /*************************** 其他可能的錯誤處理 **********************************/
			   } catch (Exception e) {
				   out.print("失敗");
			   }
			  }
		
	}

}

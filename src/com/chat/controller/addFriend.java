package com.chat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.chat.model.ChatService;
import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/addFriend")
public class addFriend extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String myMem_no = req.getParameter("myMem_no");
		String otherMem_no = req.getParameter("otherMem_no");
		ChatService chatService = new ChatService(); 
		MemService memService = new MemService();
		JSONObject json = new JSONObject();
		//檢查是否加過對方好友
		List<MemVO> myFriendList = chatService.getAllFriends(myMem_no);
		if(myFriendList.contains(memService.findByPK(otherMem_no))) {	//如果我的好友已經包含對方了
			try {
				json.put("alreadyFriend", "true");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else {
			chatService.addFrined(myMem_no, otherMem_no);  //兩方都要互加，A加B後，B也要加A
			chatService.addFrined(otherMem_no, myMem_no);
			try {
				json.put("alreadyFriend", "false");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		out.println(json);
	}

}

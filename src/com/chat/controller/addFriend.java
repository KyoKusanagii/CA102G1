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
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String myMem_no = req.getParameter("myMem_no");
		String otherMem_no = req.getParameter("otherMem_no");
		ChatService chatService = new ChatService(); 
		MemService memService = new MemService();
		JSONObject json = new JSONObject();
		//�ˬd�O�_�[�L���n��
		List<MemVO> myFriendList = chatService.getAllFriends(myMem_no);
		if(myFriendList.contains(memService.findByPK(otherMem_no))) {	//�p�G�ڪ��n�ͤw�g�]�t���F
			try {
				json.put("alreadyFriend", "true");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else {
			chatService.addFrined(myMem_no, otherMem_no);  //��賣�n���[�AA�[B��AB�]�n�[A
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

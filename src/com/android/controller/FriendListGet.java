package com.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chat.model.ChatService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.member.model.MemVO;

@WebServlet("/FriendListGet")
public class FriendListGet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public FriendListGet() {
        super();
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine())!=null) {
			jsonIn.append(line);
		}
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String mem_no = jsonObject.get("mem_no").getAsString();
		List<MemVO> friendList = new ArrayList<>();
		
		String outStr = "";
		
		ChatService service = new ChatService();
		
		friendList = service.getAllFriends(mem_no);		
		
		outStr = gson.toJson(friendList);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();	
		out.print(outStr);
	}

}

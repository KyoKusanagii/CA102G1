package com.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.follow_mem.model.Follow_memDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.live.model.LiveVO;

@WebServlet("/LiveFollowList")
public class LiveFollowList extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public LiveFollowList() {
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
		String followNO = jsonObject.get("followNO").getAsString();
		String outStr = "";
		Follow_memDAO dao = new Follow_memDAO();
		List<LiveVO> liveList = dao.getFollowAddress(followNO);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(gson.toJson(liveList));
		
	}

}

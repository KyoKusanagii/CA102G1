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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.member.model.MemService;
import com.member.model.MemVO;

/**
 * Servlet implementation class MallSearch
 */
@WebServlet("/MallSearch")
public class MallSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public MallSearch() {
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
		String searchName = jsonObject.get("searchName").getAsString();
		String outStr = "";
		
		MemService service = new MemService();
		List<MemVO> items = service.searchMall(searchName);
		
		outStr = gson.toJson(items);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(outStr);
		
	}

}

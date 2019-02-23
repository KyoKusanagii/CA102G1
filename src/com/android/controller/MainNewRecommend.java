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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.item.model.ItemDAO;
import com.item.model.ItemService;
import com.item.model.ItemVO;


@WebServlet("/MainNewRecommend")
public class MainNewRecommend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MainNewRecommend() {
        super();
 
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		
		String outStr = "";
		
		ItemDAO dao = new ItemDAO();
		List<ItemVO> items = new ArrayList<>();
		
		
		
		for(int i = 0; i<=4 ; i++) {
			ItemVO itemVO = new ItemVO();
			String random = String.format("%03d", (int)(Math.random()*115)+1);
			String ransearch = "I00" + random;
		
			
			itemVO = dao.larryGetOneItem(ransearch);
	
			items.add(itemVO);
		}
		
		outStr = gson.toJson(items);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(outStr);
	}

}

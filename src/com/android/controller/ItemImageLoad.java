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
import com.item.model.ItemService;
import com.item.model.ItemVO;


@WebServlet("/ItemImageLoad")
public class ItemImageLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ItemImageLoad() {
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
		String item_no = jsonObject.get("item_no").getAsString();
		
		
		String outStr = "";
		List<String> itemImages = new ArrayList<>();
		ItemService service = new ItemService();
		
		itemImages = service.findItemPics(item_no);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(gson.toJson(itemImages));			
	
	}

}

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

import com.android.model.BuyerOrderVO;
import com.fborder.model.FbOrderDAO;
import com.fborder.model.FbOrderVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mallorder.model.MallOrderDAO;
import com.mallorder.model.MallOrderVO;


@WebServlet("/BuyerOrderDetail")
public class BuyerOrderDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public BuyerOrderDetail() {
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
		String orderNumber = jsonObject.get("orderNumber").getAsString();
	
		FbOrderDAO fbdao = new FbOrderDAO();
		MallOrderDAO malldao = new MallOrderDAO();
		
		List<BuyerOrderVO> fbList = fbdao.getAllOrderItem(orderNumber);
		List<BuyerOrderVO> mallList = malldao.getAllOrderItem(orderNumber);
		
		List<BuyerOrderVO> totalList = new ArrayList<>();
	
		for(BuyerOrderVO vo :fbList) {
			totalList.add(vo);
		}
		
		for(BuyerOrderVO vo :mallList) {
			totalList.add(vo);
		}
		
		String outStr = "";
		
		outStr = gson.toJson(totalList);
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		out.print(outStr);
	}

}

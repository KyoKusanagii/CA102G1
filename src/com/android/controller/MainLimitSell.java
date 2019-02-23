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
import com.item.model.ItemDAO;
import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.limitSale.model.LimitSaleService;
import com.limitSale.model.LimitSaleVO;


@WebServlet("/MainLimitSell")
public class MainLimitSell extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MainLimitSell() {
        super();
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Gson gson = new Gson();
		String outStr = "";
		
		ItemDAO dao = new ItemDAO();
		
		LimitSaleService limitservice = new LimitSaleService();
		
		List<LimitSaleVO> limitList = limitservice.findHomePage();
		
		List<ItemVO> itemList = new ArrayList();
		
		
		for(LimitSaleVO vo: limitList) {
			ItemVO itemvo = new ItemVO();
			itemvo = dao.larryGetOneItem(vo.getItem_no());
			itemList.add(itemvo);
		}
		
		outStr = gson.toJson(itemList);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(outStr);
		
		
		
	}

}

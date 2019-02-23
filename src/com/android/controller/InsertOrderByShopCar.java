package com.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mallitem.model.MallItemVO;
import com.mallorder.model.MallOrderDAO;
import com.mallorder.model.MallOrderVO;

@WebServlet("/InsertOrderByShopCar")
public class InsertOrderByShopCar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public InsertOrderByShopCar() {
        super();
    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine())!=null){
			jsonIn.append(line);
		}
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);

		MallOrderVO insertStore = gson.fromJson(jsonObject.get("insertStore").getAsString(),MallOrderVO.class);
		Type listType = new TypeToken<List<MallItemVO>>() {}.getType();
		List<MallItemVO> insertItemList = gson.fromJson(jsonObject.get("insertItemList").getAsString(), listType);
		
		MallOrderDAO dao = new MallOrderDAO();
		dao.insertMallOrder(insertStore, insertItemList);
		
	}

}

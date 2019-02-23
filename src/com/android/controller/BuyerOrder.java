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


@WebServlet("/BuyerOrder")
public class BuyerOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BuyerOrder() {
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
		String memNO = jsonObject.get("memNO").getAsString();
	
		FbOrderDAO fbdao = new FbOrderDAO();
		MallOrderDAO malldao = new MallOrderDAO();
		
		List<FbOrderVO> fbList = fbdao.getMyOrderForBuyer(memNO);
		List<MallOrderVO> mallList = malldao.getMyOrderForBuyer(memNO);
		
		List<BuyerOrderVO> totalList = new ArrayList<>();
		BuyerOrderVO totalVO = null;
		
		for(FbOrderVO vo :fbList) {
			totalVO = new BuyerOrderVO();
			totalVO.setOrder_no(vo.getFb_order_no());
			totalVO.setOrder_time(vo.getFb_order_time());
			totalVO.setOrder_prc(vo.getFb_order_prc());
			totalVO.setOrder_trans(vo.getFb_order_trans());
			totalVO.setOrder_status(vo.getFb_order_status());
			totalVO.setPay_status(vo.getFb_pay_status());
			totalVO.setOrder_remark(vo.getFb_order_remark());
			totalVO.setType("FB");
			totalList.add(totalVO);
		}
		
		for(MallOrderVO vo :mallList) {
			totalVO = new BuyerOrderVO();
			totalVO.setOrder_no(vo.getMall_order_no());
			totalVO.setOrder_time(vo.getMall_order_time());
			totalVO.setOrder_prc(vo.getMall_order_prc());
			totalVO.setOrder_trans(vo.getMall_order_trans());
			totalVO.setOrder_status(vo.getMall_order_status());
			totalVO.setPay_status(vo.getMall_pay_status());
			totalVO.setOrder_remark(vo.getMall_order_remark());
			totalVO.setType("°Ó«°");
			
			totalList.add(totalVO);
		}
		
		String outStr = "";
		
		outStr = gson.toJson(totalList);
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		out.print(outStr);
		
		
	}

}

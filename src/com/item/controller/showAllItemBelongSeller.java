package com.item.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.item.model.ItemService;
import com.item.model.ItemVO;

@WebServlet("/showAllItemSeller")
public class showAllItemBelongSeller extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		String doWhat = req.getParameter("doWhat");
		String pageIndex = req.getParameter("pageIndex");
		String mem_no = req.getParameter("mem_no");
		ItemService service = new ItemService();
		JSONObject json = null;
		JSONArray jarray = new JSONArray();
	
		
		//�o��ӽ�a���Ҧ��ӫ~
		List<ItemVO> itemList = service.getAll(mem_no);
		List<ItemVO> aSubList = null;
		
		if(doWhat.equals("findItemCount")) {
			json = new JSONObject();
			try {
				json.put("count",itemList.size());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("�ӫ~�`�Ƭ�:"+itemList.size());
			out.println(json);
		}else if(doWhat.equals("listItem")) {
			
			try {
				//�p�G�^�����Ӭq�ӫ~�ƶq����6��
				aSubList = itemList.subList(((Integer.parseInt(pageIndex))*6-6), (Integer.parseInt(pageIndex))*6);	
			}catch(IndexOutOfBoundsException e){
				//�p�G�^�����Ӭq�ӫ~�ƶq����6��
				aSubList = itemList.subList(((Integer.parseInt(pageIndex))*6-6), ((Integer.parseInt(pageIndex))*6-6)+(itemList.size() % 6));
			}
			
			for(ItemVO item : aSubList) {
				json = new JSONObject();
				try {
					json.put("item_no", item.getItem_no());
					json.put("item_name", item.getItem_name());
					json.put("item_pic", service.findItemPics(item.getItem_no()).get(0));	//�|�^�ǫܦh�i�ӫ~�Ϥ��A�u���Ĥ@�i
					json.put("item_description", item.getItem_description());
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				jarray.put(json);
			}
			System.out.println(aSubList);
			out.println(jarray);
			
		}
	
	}

}

package com.android.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryDAO;
import com.category.model.CategoryVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.particulars.model.ParticularsJDBCDAO;
import com.particulars.model.ParticularsVO;

/**
 * Servlet implementation class ClassBigSearch
 */
@WebServlet("/ClassBigSearch")
public class ClassBigSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ClassBigSearch() {
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
		
		Map<String, Integer> carts = new HashMap<>();
		
		CategoryDAO dao = new CategoryDAO();
		List<CategoryVO> vos = dao.getAll();
		
		//¦W¦r´«¦¨½s¸¹
		for(CategoryVO vo: vos) {
			carts.put(vo.getCat_name(), vo.getCat_no());
		}
		
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String searchName = jsonObject.get("searchName").getAsString();
		Integer searchNumber = carts.get(searchName);
		String outStr = "";
		
		ItemService service = new ItemService();
		List<ItemVO> items = service.findByCat(searchNumber);
		
		outStr = gson.toJson(items);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(outStr);
	}

}
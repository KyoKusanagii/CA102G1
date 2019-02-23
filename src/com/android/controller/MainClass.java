package com.android.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.google.gson.Gson;


@WebServlet("/MainClass")
public class MainClass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public MainClass() {
        super();
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		
		List<String> classes = new ArrayList<>();
		CategoryService service = new CategoryService();
		List<CategoryVO> cates = service.getAll();
		
		for(CategoryVO cate : cates) {
			classes.add(cate.getCat_name());
		}
		
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.print(gson.toJson(classes));
	}

}

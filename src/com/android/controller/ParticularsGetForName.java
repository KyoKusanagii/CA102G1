package com.android.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryDAO;
import com.google.gson.Gson;
import com.particulars.model.ParticularsJDBCDAO;


@WebServlet("/ParticularsGetForName")
public class ParticularsGetForName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ParticularsGetForName() {
        super();
  
    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		
		Map<Integer, String> toName = new HashMap<>();
		
		ParticularsJDBCDAO dao = new ParticularsJDBCDAO();
		toName = dao.getNameByNO();
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();	
		out.print(gson.toJson(toName));
	}

}

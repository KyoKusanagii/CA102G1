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

import com.google.gson.Gson;
import com.member.model.MemDAO;

/**
 * Servlet implementation class MemberNOToPic
 */
@WebServlet("/MemberNOToPic")
public class MemberNOToPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberNOToPic() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		
		Map<String, byte[]> toPic = new HashMap<>();
		
		MemDAO dao = new MemDAO();
		toPic = dao.getPicByNO();
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();	
		out.print(gson.toJson(toPic));
	}

}

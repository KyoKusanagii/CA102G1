package com.android.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.member.model.MemDAO;
import com.member.model.MemVO;

/**
 * Servlet implementation class MemberNOToMartName
 */
@WebServlet("/MemberNOToMartName")
public class MemberNOToMartName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberNOToMartName() {
        super();

    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		
		List<MemVO> memberList = new ArrayList<>();
		
		Map<String, String> toName = new HashMap<>();
		
		
		MemDAO dao = new MemDAO();
		memberList = dao.getAll();
		
		
		
		for(MemVO mem : memberList) {
			toName.put(mem.getMem_no(), mem.getMem_martname());
		}
		
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();	
		out.print(gson.toJson(toName));
	}

}

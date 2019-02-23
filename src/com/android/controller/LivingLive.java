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

import com.google.gson.Gson;
import com.live.model.LiveDAO;
import com.live.model.LiveVO;
import com.member.model.MemVO;


@WebServlet("/LivingLive")
public class LivingLive extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public LivingLive() {
        super();
     
    }


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		List<LiveVO> livingList = new ArrayList<>();
		List<LiveVO> pickLivingList = new ArrayList<>();
		String outStr = "";
		
		LiveDAO dao = new LiveDAO();
		livingList = dao.getAll();
		
		
		//挑選正在直播的頻道
		for(LiveVO vo : livingList) {
			if(vo.getLive_status() == 1) {
				pickLivingList.add(vo);
			}
		}
		
		outStr = gson.toJson(pickLivingList);
		
		res.setContentType("text/heml; charset=UTF-8");
		PrintWriter out = res.getWriter();	
		out.print(outStr);		
		
		
	}

}

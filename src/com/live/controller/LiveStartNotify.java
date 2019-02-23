package com.live.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.live.model.LiveDAO;
import com.live.model.LiveVO;
import com.member.model.MemVO;


@WebServlet("/FrontEnd/live/LiveStartNotify")
public class LiveStartNotify extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LiveStartNotify() {
        super();

    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String contextPath = req.getParameter("requestURL");
		String liveAddress = req.getParameter("liveAddress");
		String nowTimestamp = req.getParameter("nowTimestamp");
	
		Timestamp time = Timestamp.valueOf(nowTimestamp);
		Integer status = 1;
		Timestamp endTime = null;
	
		
		MemVO memVO = (MemVO) req.getSession().getAttribute("memVO");
		
		System.out.println(memVO.getMem_no());
		
		LiveVO liveVO = new LiveVO();
		
		liveVO.setLive_seller_no(memVO.getMem_no());
		liveVO.setLive_address(liveAddress);
		liveVO.setLive_end_time(endTime);
		liveVO.setLive_status(status);
		liveVO.setLive_start_time(time);
		
		
		LiveDAO dao = new LiveDAO();
		String nextLiveNo = dao.insert(liveVO);
		System.out.println("nextLiveNo" + nextLiveNo);
	
		//221160448483712(online)  -- >§ïlocalhost ca102g1.tk -->§ïºô§}
		//599955533703878(offline)
		String url = "https://www.facebook.com/dialog/oauth?client_id=221160448483712&redirect_uri=https://ca102g1.tk"+req.getContextPath()+"/FrontEnd/webSocketTest.jsp&scope=email&response_type=token";
		
		req.getSession().setAttribute("liveAddr", liveAddress);
		req.getSession().setAttribute("nextLiveNo", nextLiveNo);
		
		res.sendRedirect(url);
	}

}

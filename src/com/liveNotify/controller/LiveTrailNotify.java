package com.liveNotify.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.model.NotifyMessage;
import com.follow_mem.model.Follow_memService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.member.model.MemVO;

@WebServlet("/LiveTrailNotify")
public class LiveTrailNotify extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
  
    public LiveTrailNotify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String startTime = req.getParameter("trailer_start_time");
		
		MemVO memVO = (MemVO)req.getSession().getAttribute("memVO");
		
		String uri = "ws://" + req.getServerName() +"/" + req.getServerPort() + req.getContextPath() + "/NotifySocket/" + memVO.getMem_no() ;
		 
		 
		
		Gson gson = new Gson();
		
		Follow_memService service = new Follow_memService();
		
		List<MemVO> followList = service.getAllFans(memVO.getMem_no());
		
		
		
		
		
		
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long willStartTime = c.getTimeInMillis();
		
		
		Date date = new Date(willStartTime-30000);
		
		
		
		NotifyMessage notifyMessage = new NotifyMessage();
		
		notifyMessage.setNotifyList(followList);
		
		notifyMessage.setSender(memVO.getMem_no());
		
		notifyMessage.setNotifyTitle(memVO.getMem_name() + "預計要在30秒後直播摟!!!");
		
		notifyMessage.setNotifyMessage("要準時來看喔!");
		
		notifyMessage.setNotifyTime(new Timestamp(willStartTime));
		
		notifyMessage.setType("notify");
		
		notifyMessage.setLiveAddress("*");
		
		
		String jsonOut = gson.toJson(notifyMessage);
		
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				req.setAttribute("TrailNotify", jsonOut);			
			}
			
		}, date);
		
		timer.cancel();
		
	}
	

}

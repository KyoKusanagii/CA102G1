package com.randomNewItem.controller;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


import com.item.model.ItemVO;
import com.randomNewItem.model.RandomItemService;


public class RandomItemServlet extends HttpServlet {
	
	Timer t;
	//GregorianCalendar gre = new GregorianCalendar(2018, 8, 30, 01, 00, 0);
	List<ItemVO> randomItemList;
	RandomItemService randomItemSvc = new RandomItemService();
	//要用時再解除封印
	@Override
	public void init() throws ServletException {
		
		t = new Timer();
		TimerTask task = new todo();
		
		t.scheduleAtFixedRate(task, new Date(), 30000);
		
		
	}
	class todo extends TimerTask{
		
				@Override
				public void run() {
					randomItemList = randomItemSvc.getRandomItem();
					getServletContext().setAttribute("randomItemList",randomItemList);
//				    System.out.println("refresh");
					
				}
	}
	@Override
	public void destroy() {
		t.cancel();
	}
	
}

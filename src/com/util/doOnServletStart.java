package com.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;

import com.limitSaleSub.model.LimitSaleSubService;
import com.limitSaleSub.model.LimitSaleSubVO;
import com.randomNewItem.model.RandomItemService;

import Acme.Serve.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@WebServlet("/doOnServletStart")

public class doOnServletStart extends HttpServlet implements ServletContextListener{
	
	private static final long serialVersionUID = 1L;
	private static JedisPool pool;
	private LimitSaleSubService lmSvc = null;
	
	Calendar cal2 = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR),
			new GregorianCalendar().get(Calendar.MONTH),new GregorianCalendar().get(Calendar.DAY_OF_MONTH),0,0,0);
	
	Timer t;
	Jedis jedis;
	ServletContext context;
	Integer visitCount = 0;
	TimerTask task = new TimerTask(){
		@SuppressWarnings("unchecked")
		@Override
		public void run() {	
			Calendar cal = new GregorianCalendar();	//這要寫在run裡面
			jedis = pool.getResource();
		//	System.out.println("有進來儲存人次 ");
			String storeTime = cal.get(Calendar.YEAR)+"-"+ (cal.get(Calendar.MONTH)+1) + "-" 
					+ cal.get(Calendar.DAY_OF_MONTH) + " " + ((cal.get(Calendar.HOUR_OF_DAY)<10?"0":"")+cal.get(Calendar.HOUR_OF_DAY))+":"+ ((cal.get(Calendar.MINUTE)<10?"0":"")+cal.get(Calendar.MINUTE));
		//	System.out.println(storeTime);	//storeTime，每次要儲存的時間點
			if(context.getAttribute("visitCount") == null) {
				visitCount = 0;
			}else {
				visitCount = (Integer)context.getAttribute("visitCount");
			}
			
			//而存storeTime，storeTime為欄位名稱
			jedis.hset("visitCount",storeTime,Integer.toString(((Set<String>)context.getAttribute("visitRemoteIPSet")).size()));
			int visitCountSize = jedis.hkeys("visitCount").size();	//取得所有記錄人次的比數
			List<String> cycle = (new ArrayList<>(new TreeSet<>(jedis.hkeys("visitCount")))).subList(visitCountSize-7,visitCountSize);
			context.setAttribute("cycle", cycle);
			List<Integer> visitTimes = new ArrayList<>();
			for(int i=0;i<cycle.size();i++) {
				 visitTimes.add(Integer.parseInt(jedis.hget("visitCount", cycle.get(i))));
			}
			context.setAttribute("visitTimes", visitTimes);
			((Set<String>)context.getAttribute("visitRemoteIPSet")).clear();
			jedis.close();
			
		}		
	};
	
	@Override
	public void init() {
		
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
	}
	
	@Override
	public void destroy() {
		t.cancel();
	}
	
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("得到ServletContext");
		Set<String> visitRemoteIP = new HashSet<>(); //存放所有來拜訪的IP;
		context = event.getServletContext();	//取得ServletContext
		context.setAttribute("visitRemoteIPSet",visitRemoteIP);
		t = new Timer();
		pool = JedisPoolUtil.getJedisPool();
		t.scheduleAtFixedRate(task,cal2.getTime(),1*60*1000); //5分鐘重設一次排程
		lmSvc = new LimitSaleSubService();
		Set<LimitSaleSubVO> lsList = lmSvc.getRandomFive();
		context.setAttribute("lsList", lsList);

		/* 8/16 從MemVO搬過來 */
		RandomItemService riSvc = new RandomItemService();
		int random = (1 + (int)Math.random()*6);
	
		
	}
}

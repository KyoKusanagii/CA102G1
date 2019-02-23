package com.specialForU.controller;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.item.model.ItemVO;
import com.member.model.MemVO;
import com.randomNewItem.model.RandomItemService;
import com.specialForU.model.SpecialForUService;
@WebListener()
public class SpecialForUServletListener implements HttpSessionListener {

	
	
	
	@Override
    public void sessionDestroyed(HttpSessionEvent se) {
		if(se.getSession().getAttribute("memVO") != null) {
			MemVO memVO = (MemVO) se.getSession().getAttribute("memVO");
			Integer cloth = (Integer) se.getSession().getAttribute("CLOTH_CNTS");
	    	Integer food = (Integer) se.getSession().getAttribute("FOOD_CNTS");
	    	Integer game = (Integer) se.getSession().getAttribute("GAME_CNTS");
	    	Integer outdoor = (Integer) se.getSession().getAttribute("OUTDOOR_CNTS");
	    	Integer homeelec = (Integer) se.getSession().getAttribute("HOMEELEC_CNTS");
	    	Integer threec = (Integer) se.getSession().getAttribute("THREEC_CNTS");
	    	int a[] = {cloth, food, game, outdoor, homeelec, threec};
	    	
	    	int theMost = a[0];
	    	
	    	for(int i = 0; i < a.length; i++){
	    		if(a[i] > theMost){
	    			theMost = a[i];
	    		}
	    	}
	    	SpecialForUService sfuSvc = new SpecialForUService();
	    	
	    	if (theMost == cloth) {
	    		sfuSvc.updateMemPreferenceCode(1, memVO.getMem_no());
	    	}else if(theMost == food) {
	    		sfuSvc.updateMemPreferenceCode(2, memVO.getMem_no());
	    	}else if(theMost == game) {
	    		sfuSvc.updateMemPreferenceCode(3, memVO.getMem_no());
	    	}else if(theMost == outdoor) {
	    		sfuSvc.updateMemPreferenceCode(4, memVO.getMem_no());
	    	}else if(theMost == homeelec) {
	    		sfuSvc.updateMemPreferenceCode(5, memVO.getMem_no());
	    	}else if(theMost == threec) {
	    		sfuSvc.updateMemPreferenceCode(6, memVO.getMem_no());
	    	}
	    	
			System.out.println("session expired and preference saved");
		}
		System.out.println("session destroyed");

    }
	
}

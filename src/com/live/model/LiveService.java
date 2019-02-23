package com.live.model;

import java.util.List;

public class LiveService {
	private LiveDAO_interface dao;
	
	public LiveService() {
		dao = new LiveDAO();
	}
	
	//2018/8/2 Max新增，找出那個人所有播過的影片
	public List<LiveVO> getAllLivesByASeller(String mem_no){
		return dao.getAllBelongToSeller(mem_no);
	}
	
	// 8/4 Hugh新增觀看直播區，找出正在直播的影片
	public List<LiveVO> getLiveNow(){
		return dao.getLiveNow();
	}
}

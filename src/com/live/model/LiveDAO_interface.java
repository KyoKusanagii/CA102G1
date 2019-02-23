package com.live.model;

import java.util.List;

public interface LiveDAO_interface {
	
	public String insert(LiveVO liveVO);
	public void update(LiveVO liveVO);
	public void delete(String live_no);
	public LiveVO findByPrimaryKey(String live_no);
	public List<LiveVO> getAll();
	
	//2018/8/2 Max新增，找出那個人所有播過的影片
	public List<LiveVO> getAllBelongToSeller(String mem_no);
	
	// 8/4 Hugh新增觀看直播區，找出正在直播的影片
	public List<LiveVO> getLiveNow();
}

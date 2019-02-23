package com.live.model;

import java.util.List;

public class LiveService {
	private LiveDAO_interface dao;
	
	public LiveService() {
		dao = new LiveDAO();
	}
	
	//2018/8/2 Max�s�W�A��X���ӤH�Ҧ����L���v��
	public List<LiveVO> getAllLivesByASeller(String mem_no){
		return dao.getAllBelongToSeller(mem_no);
	}
	
	// 8/4 Hugh�s�W�[�ݪ����ϡA��X���b�������v��
	public List<LiveVO> getLiveNow(){
		return dao.getLiveNow();
	}
}

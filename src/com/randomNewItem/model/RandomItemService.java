package com.randomNewItem.model;

import java.util.List;


import com.item.model.ItemVO;

public class RandomItemService {
private RandomItem_interface dao;
	
	public RandomItemService() {
		dao = new RandomItemJDBCDAO();
	}
	
	public List<ItemVO> getRandomItem() {
		return dao.findRandomItem();
	}
	
	public List<ItemVO> getRandomFiveItemsByCategory(Integer category){
		return dao.getRandomFiveItemsByCategory(category);
	}
}

package com.randomNewItem.model;

import java.util.List;
import java.util.Set;

import com.item.model.ItemVO;

public interface RandomItem_interface {
		public List<ItemVO> findRandomItem();
		public List<ItemVO> getRandomFiveItemsByCategory(Integer category);
}

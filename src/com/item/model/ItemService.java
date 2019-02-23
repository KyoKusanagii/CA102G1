package com.item.model;

import java.util.List;

public class ItemService {
	private ItemDAO_interface dao;
	
	public ItemService() {
		dao = new ItemDAO();		
	}
	
	
	public String addItem(String item_name, Integer item_price, Integer item_primary_class,  Integer item_secondary_class, String item_owner, Integer is_fb_launch, Integer is_mall_launch, Integer item_inventory, String item_description) {
		ItemVO itemVO = new ItemVO();
		itemVO.setItem_name(item_name);
		itemVO.setItem_price(item_price);
		itemVO.setItem_primary_class(item_primary_class);
		itemVO.setItem_secondary_class(item_secondary_class);
		itemVO.setItem_owner(item_owner);
		itemVO.setIs_fb_launch(is_fb_launch);
		itemVO.setIs_mall_launch(is_mall_launch);
		itemVO.setItem_inventory(item_inventory);
		itemVO.setItem_description(item_description);
		String next_itemno = dao.insert(itemVO);
		
		return next_itemno;
	}
	
	
	public ItemVO updateItem(String item_no,String item_name, Integer item_price, Integer item_primary_class,  Integer item_secondary_class, String item_owner, Integer is_fb_launch, Integer is_mall_launch, Integer item_inventory, String item_description) {
		ItemVO itemVO = new ItemVO();
		itemVO.setItem_no(item_no);
		itemVO.setItem_name(item_name);
		itemVO.setItem_price(item_price);
		itemVO.setItem_primary_class(item_primary_class);
		itemVO.setItem_secondary_class(item_secondary_class);
		itemVO.setItem_owner(item_owner);
		itemVO.setIs_fb_launch(is_fb_launch);
		itemVO.setIs_mall_launch(is_mall_launch);
		itemVO.setItem_inventory(item_inventory);
		itemVO.setItem_description(item_description);
		dao.update(itemVO);
		
		return itemVO;
	}
	
	public void deleteItem(String item_no) {
		dao.delete(item_no);
	}
	
	public ItemVO getOneItem(String item_no) {
		return dao.findByPK(item_no);
	}
	
	public List<ItemVO> getAll(String login_mem){
		return dao.getAll(login_mem);
	}
	
	public ItemVO updateLaunch(String item_no,Integer is_fb_launch, Integer is_mall_launch) {
		ItemVO itemVO = new ItemVO();
		itemVO.setItem_no(item_no);
		itemVO.setIs_fb_launch(is_fb_launch);
		itemVO.setIs_mall_launch(is_mall_launch);
		dao.updateLaunch(itemVO);
		return itemVO;
	}
	
	public List<ItemVO> findByCat(Integer item_primary_class) {
		return dao.findByCat(item_primary_class);
	}
	
	public List<ItemVO> findByPart(Integer item_secondary_class) {
		return dao.findByPart(item_secondary_class);
	}
	
	public List<ItemVO> findByKeyWord(String item_name) {
		return dao.findByKeyWord(item_name);
	}
	
	//找到所有商品圖片的方法
	public List<String> findItemPics(String item_no) {
		return dao.findItemPics(item_no);
	}
	
}

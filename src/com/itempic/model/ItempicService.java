package com.itempic.model;

import java.util.List;

public class ItempicService {
private ItempicDAO_interface dao;
	
	public ItempicService() {
		dao = new ItempicDAO();		
	}
	
	public ItempicVO insert(String item_no, byte[] item_pic) {
		ItempicVO itempic = new ItempicVO();
		itempic.setItem_no(item_no);
		itempic.setItem_pic(item_pic);
		dao.insert(itempic);

		return itempic;
	}
	
	public ItempicVO update(String item_no, String item_pic_no, byte[] item_pic) {
		ItempicVO itempic = new ItempicVO();
		itempic.setItem_no(item_no);
		itempic.setItem_pic_no(item_pic_no);
		itempic.setItem_pic(item_pic);
		dao.insert(itempic);

		return itempic;
	}
	
	public void delete(String item_pic_no) {
		dao.delete(item_pic_no);
	}
	
	public ItempicVO findByPK(String item_no, String item_pic_no) {
		return dao.findByPK(item_no, item_pic_no);
	}
	
	public List<ItempicVO> getAll() {
		return dao.getAll();
	}
	
	public List<ItempicVO> getOneItemAllPic(String item_no) {
		return dao.getOneItemAllPic(item_no);
	}
	
	public ItempicVO getThumbnail(String item_no){
		return  dao.findThumbnail(item_no);
		
	}

}

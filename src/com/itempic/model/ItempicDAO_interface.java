package com.itempic.model;

import java.util.List;

public interface ItempicDAO_interface {
	void insert(ItempicVO itempic);
	void update(ItempicVO itempic);
	void delete(String item_pic_no);
	ItempicVO findByPK(String item_no, String item_pic_no);
	List<ItempicVO> getOneItemAllPic(String item_no);
	List<ItempicVO> getAll();
	ItempicVO findThumbnail(String item_no);

}

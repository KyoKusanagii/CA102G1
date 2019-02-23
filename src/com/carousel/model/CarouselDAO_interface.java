package com.carousel.model;


import java.util.List;

public interface CarouselDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void add(CarouselVO cv);
	void update(CarouselVO cv);
	void delete(Integer carousel_no);
	CarouselVO findByPK(Integer carousel_no);
	List<CarouselVO> findByTitle(String Carousel_title);
	List<CarouselVO> getAll();

}
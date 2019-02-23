package com.carousel.model;

import java.util.ArrayList;
import java.util.List;

public class CarouselService {
	
	private CarouselDAO_interface dao;
	
	public CarouselService() {
		dao = new CarouselJDBCDAO();
	}
	
	public CarouselVO add(byte[] Carousel_pic, String Carousel_title, String Carousel_subTitle) {
		CarouselVO carouselVO = new CarouselVO();
		
		carouselVO.setCarousel_pic(Carousel_pic);
		carouselVO.setCarousel_title(Carousel_title);
		carouselVO.setCarousel_subTitle(Carousel_subTitle);
		dao.add(carouselVO);
		return carouselVO;
	}
	
	public CarouselVO update(Integer Carousel_no,byte[] Carousel_pic, String Carousel_title, String Carousel_subTitle) {
		CarouselVO carouselVO = new CarouselVO();
		
		carouselVO.setCarousel_no(Carousel_no);
		carouselVO.setCarousel_pic(Carousel_pic);
		carouselVO.setCarousel_title(Carousel_title);
		carouselVO.setCarousel_subTitle(Carousel_subTitle);
		dao.update(carouselVO);
		return carouselVO;
		
	}
	
	public void delete(Integer Carousel_no) {
		dao.delete(Carousel_no);
		
	}
	
	public CarouselVO getOne(Integer Carousel_no) {
		return dao.findByPK(Carousel_no);
	}
	
	public List<CarouselVO> getSomeByKeyword(String Carousel_title) {
		return dao.findByTitle(Carousel_title);
	}
	
	public List<CarouselVO> getAll(){
		return dao.getAll();
	}
	
	
	
}

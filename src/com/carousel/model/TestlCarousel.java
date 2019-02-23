package com.carousel.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class TestlCarousel {

	public static void main(String[] args) throws Exception {
		CarouselJDBCDAO dao = new CarouselJDBCDAO();

		// 新增
//		CaroselVO cs1 = new CarouselVO();
//		
//		File file = new File("G:\\lobdir\\4.png"); 
//		FileInputStream fis = new FileInputStream(file); 
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] bucket = new byte[fis.available()]; 
//		while (fis.read(bucket) != -1) {
//			baos.write(bucket);
//		};
//		
//		cs1.setCarousel_pic(baos.toByteArray());
//		cs1.setCarousel_title("newing a title");
//		cs1.setCarousel_subTitle("newing a subtitle");
//		dao.add(cs1);
//		
//		baos.close();
//		fis.close();

		// 修改
//		CarouselVO cs2 = new CarouselVO();
//		cs2.setCarousel_no(3);
//		
//		File file2 = new File("G:\\lobdir\\5.png"); 
//		FileInputStream fis2 = new FileInputStream(file2); 
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		byte[] bucket2 = new byte[fis2.available()]; 
//		while(fis2.read(bucket2)!= -1) {
//			baos2.write(bucket2);
//		}
//		
//		cs2.setCarousel_pic(baos2.toByteArray());
//		cs2.setCarousel_title("aaa");
//		cs2.setCarousel_subTitle("bbb");
//		dao.update(cs2);
//		
//		baos2.close();
//		fis2.close();

//		// 刪除
//		dao.delete(2);
//
//		// 查詢by no
//		CarouselVO cs3 = dao.findByPK(1);
//		System.out.print(cs3.getCarousel_no() + ",");
//		System.out.print(cs3.getCarousel_pic() + ",");
//		System.out.print(cs3.getCarousel_title() + ",");
//		System.out.println(cs3.getCarousel_subTitle() + ",");
//		System.out.println("---------------------");
//
//		// 查詢all
//		List<CarouselVO> list = dao.getAll();
//		for (CarouselVO cs4 : list) {
//			System.out.print(cs4.getCarousel_no() + ",");
//			System.out.print(cs4.getCarousel_pic() + ",");
//			System.out.print(cs4.getCarousel_title() + ",");
//			System.out.print(cs4.getCarousel_subTitle() + ",");
//			System.out.println();
//		}
		
		//查詢by title keyword
		List<CarouselVO> list = dao.findByTitle("xx");
		for (CarouselVO cs5 : list ) {
		System.out.println(cs5.getCarousel_no());
		System.out.println(cs5.getCarousel_pic());
		System.out.println(cs5.getCarousel_title());
		System.out.println(cs5.getCarousel_subTitle());
		
		}
	}

}

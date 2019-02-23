package com.limitSaleSub.model;

import java.util.Set;

public class TestlLimitSaleSub {

	public static void main(String[] args) {
		LimitSaleSubJDBCDAO dao = new LimitSaleSubJDBCDAO();


		// ¬d¸ß
//		LimitSaleVO ls3 = dao.findByPK(1005);
//		System.out.print(ls3.getSale_no() + ",");
//		System.out.print(ls3.getItem_no() + ",");
//		System.out.print(ls3.getSale_start() + ",");
//		System.out.print(ls3.getSale_end() + ",");
//		System.out.println(ls3.getSale_price() + ",");
//		System.out.println("---------------------");

		// ¬d¸ß
//		List<LimitSaleVO> list = dao.getRandomFour();
//		for (LimitSaleVO ls4 : list) {
//			System.out.print(ls4.getSale_no() + ",");
//			System.out.print(ls4.getItem_no() + ",");
//			System.out.print(ls4.getSale_start() + ",");
//			System.out.print(ls4.getSale_end() + ",");
//			System.out.print(ls4.getSale_price() + ",");
//			System.out.println(ls4.getSale_remark() + ",");
//			System.out.println(ls4.getItem_pic() + ",");
//			System.out.println(ls4.getItem_owner() + ",");
//			System.out.println(ls4.getItem_name() + ",");
//			System.out.println(ls4.getItem_price() + ",");
//			System.out.println();
//		}
//		LimitSaleVO ls5 = dao.findByItemNo("I00001");
//			System.out.print(ls5.getSale_no() + ",");
//			System.out.print(ls5.getItem_no() + ",");
//			System.out.print(ls5.getSale_start() + ",");
//			System.out.print(ls5.getSale_end() + ",");
//			System.out.print(ls5.getSale_price() + ",");
//			System.out.println(ls5.getSale_remark() + ",");
//			System.out.println(ls5.getItem_pic() + ",");
//			System.out.println(ls5.getItem_owner() + ",");
//			System.out.println(ls5.getItem_name() + ",");
//			System.out.println(ls5.getItem_price() + ",");
//			System.out.println();
		
		Set<LimitSaleSubVO> ls5 = dao.getAll();
		for (LimitSaleSubVO lmVO : ls5) {
		System.out.print(lmVO.getSale_no() + ",");
		System.out.print(lmVO.getItem_no() + ",");
		System.out.print(lmVO.getSale_start() + ",");
		System.out.print(lmVO.getSale_end() + ",");
		System.out.print(lmVO.getSale_price() + ",");
		System.out.println(lmVO.getSale_remark() + ",");
		System.out.println(lmVO.getItem_pic() + ",");
		System.out.println(lmVO.getItem_owner() + ",");
		System.out.println(lmVO.getItem_name() + ",");
		System.out.println(lmVO.getItem_price() + ",");
		System.out.println();
		};
		
		
	}

}

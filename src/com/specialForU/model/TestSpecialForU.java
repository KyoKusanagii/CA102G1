package com.specialForU.model;

import java.util.List;

public class TestSpecialForU {

	public static void main(String[] args) {
		SpecialForUJDBCDAO dao = new SpecialForUJDBCDAO();

		// 新增
		SpecialForUVO sf = new SpecialForUVO();
		sf.setMem_no("999");
		sf.setCloth_cnts(5);
		sf.setFood_cnts(5);
		sf.setGame_cnts(5);
		sf.setOutdoor_cnts(5);
		sf.setHomeelec_cnts(5);
		sf.setThreec_cnts(5);
		dao.add(sf);

		// 修改
		SpecialForUVO sf2 = new SpecialForUVO();
		sf2.setMem_no("M00010");
		sf2.setCloth_cnts(100);
		sf2.setFood_cnts(100);
		sf2.setGame_cnts(100);
		sf2.setOutdoor_cnts(100);
		sf2.setHomeelec_cnts(100);
		sf2.setThreec_cnts(100);
		dao.update(sf2);

		// 刪除
		dao.delete("M00009");

		// 查詢
		SpecialForUVO sf3 = dao.findByPK("999");
		System.out.print(sf3.getMem_no() + ",");
		System.out.print(sf3.getCloth_cnts() + ",");
		System.out.print(sf3.getFood_cnts() + ",");
		System.out.print(sf3.getGame_cnts() + ",");
		System.out.print(sf3.getOutdoor_cnts() + ",");
		System.out.print(sf3.getHomeelec_cnts() + ",");
		System.out.print(sf3.getThreec_cnts() + ",");
		System.out.println("---------------------");

		// 查詢
		List<SpecialForUVO> list = dao.getAll();
		for (SpecialForUVO sf4 : list) {
			System.out.print(sf4.getMem_no() + ",");
			System.out.print(sf4.getCloth_cnts() + ",");
			System.out.print(sf4.getFood_cnts() + ",");
			System.out.print(sf4.getGame_cnts() + ",");
			System.out.print(sf4.getOutdoor_cnts() + ",");
			System.out.print(sf4.getHomeelec_cnts() + ",");
			System.out.print(sf4.getThreec_cnts() + ",");
			System.out.println();
		}
	}

}

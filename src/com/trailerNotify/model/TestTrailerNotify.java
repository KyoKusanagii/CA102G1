package com.trailerNotify.model;

import java.util.List;

public class TestTrailerNotify {

	public static void main(String[] args) {
		TrailerNotifyJDBCDAO dao = new TrailerNotifyJDBCDAO();

		// �s�W
		TrailerNotifyVO tn = new TrailerNotifyVO();
		tn.setTrailer_No("999");
		tn.setMem_No("999");
		dao.add(tn);

		// �ק�
		TrailerNotifyVO tn2 = new TrailerNotifyVO();
		tn2.setTrailer_No("T00010");
		tn2.setMem_No("888");
		dao.update(tn2);

		// �R��
		dao.delete("T00009");

		// �d��
		TrailerNotifyVO tn3 = dao.findByPK("999");
		System.out.print(tn3.getTrailer_No() + ",");
		System.out.print(tn3.getMem_No() + ",");
		System.out.println("---------------------");

		// �d��
		List<TrailerNotifyVO> list = dao.getAll();
		for (TrailerNotifyVO tn4 : list) {
			System.out.print(tn4.getTrailer_No() + ",");
			System.out.print(tn4.getMem_No() + ",");
			System.out.println();
		}
	}

}

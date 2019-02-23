package com.liveNotify.model;

import java.util.List;

public class TestlLiveNotify {

	public static void main(String[] args) {
		LiveNotifyJDBCDAO dao = new LiveNotifyJDBCDAO();

		// 新增
		LiveNotifyVO ln = new LiveNotifyVO();
		ln.setLive_no("999");
		ln.setMem_No("999");
		dao.add(ln);

		// 修改
		LiveNotifyVO ln2 = new LiveNotifyVO();
		ln2.setLive_no("20180718-L00001");
		ln2.setMem_No("999");
		dao.update(ln2);

		// 刪除
		dao.delete("20180718-L00005");

		// 查詢
		LiveNotifyVO ln3 = dao.findByPK("20180718-L00003");
		System.out.print(ln3.getLive_no() + ",");
		System.out.print(ln3.getMem_No() + ",");
		System.out.println("---------------------");

		// 查詢
		List<LiveNotifyVO> list = dao.getAll();
		for (LiveNotifyVO ln4 : list) {
			System.out.print(ln4.getLive_no() + ",");
			System.out.print(ln4.getMem_No() + ",");
			System.out.println();
		}
	}

}

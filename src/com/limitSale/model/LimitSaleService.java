package com.limitSale.model;

import java.sql.Timestamp;
import java.util.List;

public class LimitSaleService {
	private LimitSaleDAO_interface dao;

	public LimitSaleService() {
		dao = new LimitSaleDAO();
	}

	public void insert(String item_no, Timestamp sale_start, Timestamp sale_end, Integer sale_price,
			Integer sale_status, String sale_remark) {
		LimitSaleVO limitSaleVO = new LimitSaleVO();
		limitSaleVO.setItem_no(item_no);
		limitSaleVO.setSale_start(sale_start);
		limitSaleVO.setSale_end(sale_end);
		limitSaleVO.setSale_price(sale_price);
		limitSaleVO.setSale_status(sale_status);
		limitSaleVO.setSale_remark(sale_remark);
		dao.add(limitSaleVO);
	}

	public void update(Integer sale_no, String item_no, Timestamp sale_start, Timestamp sale_end, Integer sale_price,
			Integer sale_status, String sale_remark) {
		LimitSaleVO limitSaleVO = new LimitSaleVO();
		limitSaleVO.setSale_no(sale_no);
		limitSaleVO.setItem_no(item_no);
		limitSaleVO.setSale_start(sale_start);
		limitSaleVO.setSale_end(sale_end);
		limitSaleVO.setSale_price(sale_price);
		limitSaleVO.setSale_status(sale_status);
		limitSaleVO.setSale_remark(sale_remark);
		dao.update(limitSaleVO);

	}

	public void delete(Integer sale_no) {
		dao.delete(sale_no);
	}

	public LimitSaleVO findByPK(Integer sale_no) {
		return dao.findByPK(sale_no);
	}

	public LimitSaleVO findOneSale(String item_no) {
		return dao.findOneSale(item_no);
	}

	public List<LimitSaleVO> getAll() {
		return dao.getAll();

	}
	
	public List<LimitSaleVO> findHomePage() {
		return dao.findHomePage();
	}

	public void offLimit(Integer sale_no) {
		dao.offLimit(sale_no);
		
	}

}

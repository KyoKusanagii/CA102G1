package com.trailer.model;

import java.sql.Timestamp;
import java.util.List;

public class TrailerService {
	private TrailerDAO_interface dao;

	public TrailerService() {
		dao = new TrailerDAO();
	}

	public void insert(String trailer_seller_no, Timestamp trailer_time, String trailer_topic) {
		TrailerVO trailerVO = new TrailerVO();
		trailerVO.setTrailer_seller_no(trailer_seller_no);
		trailerVO.setTrailer_time(trailer_time);
		trailerVO.setTrailer_topic(trailer_topic);
		dao.insert(trailerVO);
	}

	public void update(String trailer_no, String trailer_seller_no, Timestamp trailer_time, String trailer_topic) {
		TrailerVO trailerVO = new TrailerVO();
		trailerVO.setTrailer_no(trailer_no);
		trailerVO.setTrailer_seller_no(trailer_seller_no);
		trailerVO.setTrailer_time(trailer_time);
		trailerVO.setTrailer_topic(trailer_topic);
		dao.update(trailerVO);

	}

	public void delete(String trailer_no) {
		dao.delete(trailer_no);
	}

	public TrailerVO findByPK(String trailer_seller_no) {
		return dao.findByPrimaryKey(trailer_seller_no);
	}

	public List<TrailerVO> getAll() {
		return dao.getAll();

	}

}

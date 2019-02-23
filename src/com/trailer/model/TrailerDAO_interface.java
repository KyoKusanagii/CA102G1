package com.trailer.model;

import java.util.List;

public interface TrailerDAO_interface {
	
	public void insert(TrailerVO trailerVO);
	public void update(TrailerVO trailerVO);
	public void delete(String trailer_no);
	public TrailerVO findByPrimaryKey(String trailer_no);
	public List<TrailerVO> getAll();
}

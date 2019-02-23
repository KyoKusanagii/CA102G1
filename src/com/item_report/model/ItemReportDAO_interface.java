package com.item_report.model;

import java.util.List;

public interface ItemReportDAO_interface {
	public void insert(ItemReportVO itemReportVO);
	public void update(String itemReportNo,String itemReportHandler,int itemReportStatus);
	public void delete(String itemReportNO);
	ItemReportVO findByPrimaryKey(String itemReportNO);
	public List<ItemReportVO> getAll();
	public List<String> getAllName();
}

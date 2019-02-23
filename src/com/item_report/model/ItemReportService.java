package com.item_report.model;

import java.util.List;

public class ItemReportService {
	
	private ItemReportDAO_interface dao;
	
	public ItemReportService() {
		
		dao = new ItemReportDAO();
	}

	public void addItemReport(String itemNo,String itemReporter,String itemOwner,String itemReportDescription,
			String itemReasons,Integer reportStatus,byte[] itemPic) {
		ItemReportVO itemReport = new ItemReportVO();
		itemReport.setItem_NO(itemNo);
		itemReport.setMem_NO(itemReporter);
		itemReport.setEmp_NO(null);
		itemReport.setReport_Description(itemReportDescription);
		itemReport.setReport_reasons(itemReasons);
		itemReport.setReport_Status(0);
		if(itemPic == null)
			itemReport.setReportPic(null);
		else
			itemReport.setReportPic(itemPic);
		dao.insert(itemReport);
	}
	public void updateItemReport(String itemReportNo,String itemReportHandler,int itemReportStatus) {	
		dao.update(itemReportNo, itemReportHandler, itemReportStatus);	
	}
	public List<ItemReportVO> getAllItemReport(){
		
		 List<ItemReportVO> itemReportList = dao.getAll();
		 
		 return itemReportList;
	}
	
	//取得所有被檢舉的商品名稱
	public List<String> getAllItemNameofReport(){
		
		 List<String> itemNameOfReportList = dao.getAllName();
		 
		 return itemNameOfReportList;
	}
	
	public ItemReportVO findOneReport(String itemReportNo) {
		return dao.findByPrimaryKey(itemReportNo);
		
	}
}

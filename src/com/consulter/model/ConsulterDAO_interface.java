package com.consulter.model;

import java.util.List;

public interface ConsulterDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(ConsulterVO consulter);
	void update(ConsulterVO  consulter);
	void delete(String  consulter_no);
	ConsulterVO findByPK(String consulter_no);
	List<ConsulterVO> getAll();

}
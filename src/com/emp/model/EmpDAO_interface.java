package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
	// 此介面定義對資料庫的相關存取抽象方法
	void insert(EmpVO emp);
	void update(EmpVO emp);
	void delete(String emp_no);
	EmpVO findByPK(String emp_no);
	List<EmpVO> getAll();

}
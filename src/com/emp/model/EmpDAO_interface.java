package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
	// �������w�q���Ʈw�������s����H��k
	void insert(EmpVO emp);
	void update(EmpVO emp);
	void delete(String emp_no);
	EmpVO findByPK(String emp_no);
	List<EmpVO> getAll();

}
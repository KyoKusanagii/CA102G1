package com.member.model;

import java.sql.Date;
import java.util.List;


public class MemService {
	
private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemDAO();		
	}
	
	public MemVO signup(String mem_id, String mem_name, String mem_psw, String mem_email, byte[] mem_profilepic, byte[] mem_martcover) {
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		memVO.setMem_name(mem_name);
		memVO.setMem_psw(mem_psw);
		memVO.setMem_email(mem_email);
		memVO.setMem_profilepic(mem_profilepic);
		memVO.setMem_martcover(mem_martcover);
		memVO.setMem_condition(0);
		memVO.setMem_artauth(0);
		dao.signup(memVO);
		
		return memVO;
	} 
	
	
	public MemVO setAuth(String mem_no, Integer mem_condition, Integer mem_artauth) {
		MemVO memVO = new MemVO();
		memVO.setMem_condition(mem_condition);
		memVO.setMem_artauth(mem_artauth);
		memVO.setMem_no(mem_no);
		dao.setAuth(memVO);
		
		return memVO;
	}
	
	
	public void deleteItem(String item_no) {
		dao.delete(item_no);
	}
	
	public MemVO findByPK(String mem_no) {
		return dao.findByPK(mem_no);
	}
	
	public List<MemVO> getAll(){
		return dao.getAll();
	}
	
	public List<MemVO> getAllDesc(){
		return dao.getAllDesc();
	}
	
	public MemVO login(String mem_id, String mem_psw) {
		return dao.login(mem_id, mem_psw);
	}
	
	// Hugh 7/29�s�W:�T�{�b���O�_�s�b
	public MemVO checkID(String mem_id) {
		return dao.checkID(mem_id);
	}
		
		// For Larry
	public MemVO findByID(String mem_id) {
		return dao.findByID(mem_id);
	}
	// Hugh 8/1�s�W:�M����
	public List<MemVO> searchMall(String mem_martname) {
		return dao.searchMall(mem_martname);
	}
	
	// Max 8/5 ��X�[�J���̷s�|���s�� 
	public String findNewestMember() {
		return dao.findNewestMember();
	}
	
	// 8/7 Hugh�s�W�H�c����
	public MemVO activeMem(String mem_id) {
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		dao.activeMem(memVO);
		
		return memVO;
	}
	
	//�|���ק�ۤv�|�����
	public MemVO updateMem(String mem_email, String mem_name,String mem_mobile, String mem_sex,String mem_post, String mem_address, Date mem_birth, byte[] mem_profilepic, String mem_no) {
		MemVO memVO = new MemVO();
		
		memVO.setMem_email(mem_email);
		memVO.setMem_name(mem_name);
		memVO.setMem_mobile(mem_mobile);
		memVO.setMem_sex(mem_sex);
		memVO.setMem_post(mem_post);
		memVO.setMem_address(mem_address);
		memVO.setMem_birth(mem_birth);
		memVO.setMem_profilepic(mem_profilepic);
		memVO.setMem_no(mem_no);
		dao.updateMem(memVO);
		
		return memVO;
	}
	
	//�|���ק�ۤv������
	public MemVO updateStore(String mem_martname, String mem_martinfo, byte[] mem_martcover, String mem_activecode,String mem_no) {
		MemVO memVO = new MemVO();
		memVO.setMem_martname(mem_martname);
		memVO.setMem_martinfo(mem_martinfo);
		memVO.setMem_martcover(mem_martcover);
		memVO.setMem_activecode(mem_activecode);
		memVO.setMem_no(mem_no);
		dao.updateStore(memVO);
		
		return memVO;
	}
	//�ק�K�X
	public MemVO updatePsw(String mem_psw, String mem_no) {
		MemVO memVO = new MemVO();
		memVO.setMem_psw(mem_psw);
		memVO.setMem_no(mem_no);
		dao.updatePsw(memVO);
		
		return memVO;
	}
	
	
	public MemVO fbSignup(String mem_id, String mem_name, String mem_psw, String mem_email, String mem_fbid, byte[] mem_profilepic, byte[] mem_martcover) {
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		memVO.setMem_name(mem_name);
		memVO.setMem_psw(mem_psw);
		memVO.setMem_email(mem_email);
		memVO.setMem_fbid(mem_fbid);
		memVO.setMem_profilepic(mem_profilepic);
		memVO.setMem_martcover(mem_martcover);
		memVO.setMem_condition(1);
		memVO.setMem_artauth(1);
		dao.fbSignup(memVO);
		
		return memVO;
	}
	
	// 8/11Hugh�s�W�GFB�����L�Ϥ����ܡA��Ʈw��ۧ�s
	public MemVO fbNamePicture(String mem_name, byte[] mem_profilepic) {
		MemVO memVO = new MemVO();
		memVO.setMem_name(mem_name);
		memVO.setMem_profilepic(mem_profilepic);
		dao.fbNamePicture(memVO);
		
		return memVO;
	}
		
	// 8/14Hugh�s�W�G�T�{�W�r���L���U�L
	public MemVO checkFBName(String mem_name) {
		return dao.checkFBName(mem_name);
	}
	
	// 8/14Hugh�s�W�GFB�ΦW�r�n�J
	public MemVO fbLogin(String mem_name, String mem_psw) {
		return dao.fbLogin(mem_name, mem_psw);
	}
	
	// 8/14Hugh�s�W�G�T�{�W�r���L���U�L
	public MemVO fbFullInfo(String mem_name, String mem_id, String mem_email, String mem_fbid, byte[] mem_profilepic) {
		MemVO memVO = new MemVO();
		memVO.setMem_name(mem_name);
		memVO.setMem_id(mem_id);
		memVO.setMem_email(mem_email);
		memVO.setMem_fbid(mem_fbid);
		memVO.setMem_profilepic(mem_profilepic);
		dao.fbFullInfo(memVO);
		
		return memVO;
	}
	
	//�q�榨�߫�,�ק�receive address
	public MemVO updateReceiveAdd(String mem_receiveadd, String mem_no) {
		MemVO memVO = new MemVO();
		memVO.setMem_receiveadd(mem_receiveadd);
		memVO.setMem_no(mem_no);
		dao.updateReceiveadd(memVO);
		
		return memVO;
	}
}

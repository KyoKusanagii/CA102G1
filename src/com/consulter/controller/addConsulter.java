package com.consulter.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.consulter.model.ConsulterService;
import com.consulter.model.ConsulterVO;

@WebServlet("/addConsulter")
public class addConsulter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // �B�z�����ɦW
		res.setContentType("text/html; charset=UTF-8");
		
		String cosulterNO = req.getParameter("cosulterNO");
		String consulterName = req.getParameter("consulterName");
		String consulterIcon = req.getParameter("consulterIcon");
		ConsulterService service = new ConsulterService();
		List<ConsulterVO> consulterList = service.getAllConsulter();
		
		//�P�_�o�ӷ|�����e�O�_���[�L�ȪA�C��
		if(consulterList.contains(new ConsulterVO(cosulterNO,consulterName, Base64.getDecoder().decode(consulterIcon)))) {
			System.out.println("���|���w�g�s�W�ܫȪA�C��A����A�[�i�h�F");
			return;
		}else {												//�A��j�Y�K�Ѧ�byte�}�C�s���Ʈw
			service.addConsulter(cosulterNO, consulterName, Base64.getDecoder().decode(consulterIcon));
		}
		
	}

}

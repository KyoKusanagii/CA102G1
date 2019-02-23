package com.limitSale.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.limitSale.model.LimitSaleService;
import com.limitSale.model.LimitSaleVO;

@WebServlet("/FrontEnd/LimitSale.do")
public class LimitSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LimitSaleServlet() {
		super();

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("getOne_For_Insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��
																// �i/dept/listEmps_ByDeptno.jsp�j �� �i
																// /dept/listAllDept.jsp�j
			req.setAttribute("requestURL", requestURL); // �e�X�ק諸�ӷ��������|, �s�Jreq

			String whichPage = req.getParameter("whichPage"); // �e�X�ק諸�ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // �e�X�ק諸�ӷ��������ĴX��, �s�Jreq(�u�Ω�:istAllEmp.jsp)

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String itemno = req.getParameter("item_no");

				/*************************** 2.�}�l�d�߸�� *****************************************/
				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("itemVO", itemVO);
				String url = "/FrontEnd/item/limitSale.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��
																// �i/dept/listEmps_ByDeptno.jsp�j �� �i
																// /dept/listAllDept.jsp�j
			req.setAttribute("requestURL", requestURL); // �e�X�ק諸�ӷ��������|, �s�Jreq

			String whichPage = req.getParameter("whichPage"); // �e�X�ק諸�ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // �e�X�ק諸�ӷ��������ĴX��, �s�Jreq(�u�Ω�:istAllEmp.jsp)

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String itemno = req.getParameter("item_no");

				/*************************** 2.�}�l�d�߸�� *****************************************/
				LimitSaleService itemSvc = new LimitSaleService();
				LimitSaleVO limitSaleVO = itemSvc.findOneSale(itemno);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("limitSaleVO", limitSaleVO);
				String url = "/FrontEnd/item/update_sale.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�po_arti.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��
																// �i/dept/listEmps_ByDeptno.jsp�j �� �i
																// /dept/listAllDept.jsp�j
			req.setAttribute("requestURL", requestURL); // �e�X�ק諸�ӷ��������|, �s�Jreq

			String whichPage = req.getParameter("whichPage"); // �e�X�ק諸�ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

				String sale_remark = req.getParameter("sale_remark");
				if (sale_remark == null || sale_remark.trim().length() == 0) {
					errorMsgs.add("�S�椺�e: �ФŪť�");
				}

				Integer sale_price;
				try {
					sale_price = new Integer(req.getParameter("sale_price").trim());
				} catch (NumberFormatException e) {
					sale_price = 0;
					errorMsgs.add("����ж�Ʀr");
				}

				String item_no = req.getParameter("item_no");
				Integer sale_status;
				try {
					sale_status = new Integer(req.getParameter("sale_status"));
				} catch (NumberFormatException e) {
					sale_status = 0;
				}

				String limitSale_start_date = req.getParameter("limitSale_start_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("�}�l��� : �ФŪť�");
				}
				String limitSale_start_time = req.getParameter("limitSale_start_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("�}�l�ɶ�: �ФŪť�");
				}

				String limitSale_end_date = req.getParameter("limitSale_end_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("������� : �ФŪť�");
				}
				String limitSale_end_time = req.getParameter("limitSale_end_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("�����ɶ�: �ФŪť�");
				}

				String hr_s = limitSale_start_time.substring(0, 2);
				String min_s = limitSale_start_time.substring(5, 7);
				String sec_s = limitSale_start_time.substring(10, 12);
				limitSale_start_time = hr_s + ":" + min_s + ":" + sec_s;

				Timestamp sale_start = java.sql.Timestamp.valueOf(limitSale_start_date + " " + limitSale_start_time);

				String hr_e = limitSale_end_time.substring(0, 2);
				String min_e = limitSale_end_time.substring(5, 7);
				String sec_e = limitSale_end_time.substring(10, 12);
				limitSale_end_time = hr_e + ":" + min_e + ":" + sec_e;

				Timestamp sale_end = java.sql.Timestamp.valueOf(limitSale_end_date + " " + limitSale_end_time);

				if (sale_end.getTime() < sale_start.getTime())
					errorMsgs.add("�����ɶ��Фj��}�l�ɶ�");

				LimitSaleVO limitSaleVO = new LimitSaleVO();
				limitSaleVO.setItem_no(item_no);
				limitSaleVO.setSale_start(sale_start);
				limitSaleVO.setSale_end(sale_end);
				limitSaleVO.setSale_price(sale_price);
				limitSaleVO.setSale_status(sale_status);
				limitSaleVO.setSale_remark(sale_remark);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("limitSaleVO", limitSaleVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/limitSale.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				LimitSaleService limitSaleSvc = new LimitSaleService();
				limitSaleSvc.insert(item_no, sale_start, sale_end, sale_price, sale_status, sale_remark);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/limitSale.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // �e�X�ק諸�ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��
																// �i/dept/listEmps_ByDeptno.jsp�j �� �i
																// /dept/listAllDept.jsp�j
			req.setAttribute("requestURL", requestURL); // �e�X�ק諸�ӷ��������|, �s�Jreq

			String whichPage = req.getParameter("whichPage"); // �e�X�ק諸�ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/

				int sale_no = new Integer(req.getParameter("sale_no"));
				String sale_remark = req.getParameter("sale_remark");
				if (sale_remark == null || sale_remark.trim().length() == 0) {
					errorMsgs.add("�S�椺�e: �ФŪť�");
				}

				Integer sale_price;
				try {
					sale_price = new Integer(req.getParameter("sale_price").trim());
				} catch (NumberFormatException e) {
					sale_price = 0;
					errorMsgs.add("����ж�Ʀr");
				}

				String item_no = req.getParameter("item_no");
				Integer sale_status;
				try {
					sale_status = new Integer(req.getParameter("sale_status"));
				} catch (NumberFormatException e) {
					sale_status = 0;
				}

				String limitSale_start_date = req.getParameter("limitSale_start_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("�}�l��� : �ФŪť�");
				}
				String limitSale_start_time = req.getParameter("limitSale_start_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("�}�l�ɶ�: �ФŪť�");
				}

				String limitSale_end_date = req.getParameter("limitSale_end_date");
				if (limitSale_start_date == null || limitSale_start_date.trim().length() == 0) {
					errorMsgs.add("������� : �ФŪť�");
				}
				String limitSale_end_time = req.getParameter("limitSale_end_time");
				if (limitSale_start_time == null || limitSale_start_time.trim().length() == 0) {
					errorMsgs.add("�����ɶ�: �ФŪť�");
				}

				LimitSaleVO limitSaleVO = new LimitSaleVO();
				limitSaleVO.setSale_no(sale_no);
				limitSaleVO.setItem_no(item_no);
				// limitSaleVO.setSale_start(sale_start);
				// limitSaleVO.setSale_end(sale_end);
				limitSaleVO.setSale_price(sale_price);
				limitSaleVO.setSale_status(sale_status);
				limitSaleVO.setSale_remark(sale_remark);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("limitSaleVO", limitSaleVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/update_sale.jsp");
					failureView.forward(req, res);
					return;
				}

				String hr_s = limitSale_start_time.substring(0, 2);
				String min_s = limitSale_start_time.substring(5, 7);
				String sec_s = limitSale_start_time.substring(10, 12);
				limitSale_start_time = hr_s + ":" + min_s + ":" + sec_s;

				Timestamp sale_start = java.sql.Timestamp.valueOf(limitSale_start_date + " " + limitSale_start_time);

				String hr_e = limitSale_end_time.substring(0, 2);
				String min_e = limitSale_end_time.substring(5, 7);
				String sec_e = limitSale_end_time.substring(10, 12);
				limitSale_end_time = hr_e + ":" + min_e + ":" + sec_e;

				Timestamp sale_end = java.sql.Timestamp.valueOf(limitSale_end_date + " " + limitSale_end_time);

				if (sale_end.getTime() < sale_start.getTime()) {
					errorMsgs.add("�����ɶ��Фj��}�l�ɶ�");
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/update_sale.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				LimitSaleService limitSaleSvc = new LimitSaleService();
				limitSaleSvc.update(sale_no, item_no, sale_start, sale_end, sale_price, sale_status, sale_remark);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = requestURL + "?whichPage=" + whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/limitSale.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
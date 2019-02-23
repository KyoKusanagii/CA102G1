package com.item.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONArray;

import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.google.gson.JsonObject;
import com.item.model.ItemService;
import com.item.model.ItemVO;
import com.itempic.model.ItempicService;
import com.itempic.model.ItempicVO;
import com.limitSale.model.LimitSaleService;
import com.particulars.model.ParticularsService;
import com.particulars.model.ParticularsVO;

@WebServlet("/FrontEnd/item/ItemLaunch.do")
@MultipartConfig
public class ItemLaunchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ItemLaunchServlet() {
		super();

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		
		
		if("FB".equals(action)) {
			
			PrintWriter out = res.getWriter();
			
			ParticularsService particularsService  = new ParticularsService();
			CategoryService categoryService = new CategoryService();
			Integer cat_no = Integer.parseInt(req.getParameter("cat_no"));
			CategoryVO categoryVO = categoryService.findByPK(cat_no);
			List<ParticularsVO> list =particularsService.getAllByCagNo(cat_no);
			JSONArray jsonArray= new JSONArray(list);
			
			out.println(jsonArray);
			
			
		}
		
		if("ItemAll".equals(action)) {
			
			if(req.getSession().getAttribute("memVO") != null) {
				res.sendRedirect(req.getContextPath() + "/FrontEnd/item/ItemAll.jsp");
			}
				
			else {
				
				req.setAttribute("location","/FrontEnd/item/ItemAll.jsp");
				RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/member/login.jsp");
				successView.forward(req, res);


			}
			
		}

		if ("getOne_For_Update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)
			


			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String itemno = req.getParameter("item_no");

				/*************************** 2.開始查詢資料 *****************************************/
				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("itemVO", itemVO);
				String url = "/FrontEnd/item/update_item.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String item_name = req.getParameter("item_name");
				// String item_name_Reg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,}$";
				if (item_name == null || item_name.trim().length() == 0) {
					errorMsgs.add("商品名稱請勿空白");
				} /*
					 * else if(!item_name.trim().matches(item_name_Reg)) {
					 * errorMsgs.add("商品名稱只能是中文、英文或數字組成，並且要一個字以上"); }
					 */

				Integer item_price;

				try {
					item_price = new Integer(req.getParameter("item_price").trim());
				} catch (NumberFormatException e) {
					item_price = 0;
					errorMsgs.add("價格請填數字");
				}

				Integer item_primary_class;

				try {
					item_primary_class = new Integer(req.getParameter("item_primary_class").trim());
				} catch (NumberFormatException e) {
					item_primary_class = 0;
					errorMsgs.add("主類別錯誤");
				}

				Integer item_secondary_class;

				try {
					item_secondary_class = new Integer(req.getParameter("item_secondary_class").trim());
				} catch (NumberFormatException e) {
					item_secondary_class = 0;
					errorMsgs.add("副類別錯誤");
				}

				String item_owner = req.getParameter("item_owner");
				// String item_owner_Reg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,}$";
				// if(item_owner == null || item_owner.trim().length() == 0) {
				// errorMsgs.add("商品擁有者請勿空白");
				// }

				Integer is_fb_launch = 1; // 預設新增就上架
				Integer is_mall_launch = 1;

				Integer item_inventory;

				try {
					item_inventory = new Integer(req.getParameter("item_inventory").trim());
				} catch (NumberFormatException e) {
					item_inventory = 0;
					errorMsgs.add("庫存請輸入數字");
				}

				String item_description = req.getParameter("item_description");
				// String item_description_Reg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{1,}$";
				if (item_description == null || item_description.trim().length() == 0) {
					errorMsgs.add("商品描述請勿空白");
				}

				ItemVO itemVO = new ItemVO();

				itemVO.setItem_name(item_name);
				itemVO.setItem_price(item_price);
				itemVO.setItem_primary_class(item_primary_class);
				itemVO.setItem_secondary_class(item_secondary_class);
				itemVO.setItem_owner(item_owner);
				itemVO.setIs_fb_launch(is_fb_launch);
				itemVO.setIs_mall_launch(is_mall_launch);
				itemVO.setItem_inventory(item_inventory);
				itemVO.setItem_description(item_description);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemVO", itemVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/addItem.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				// 新增商品文字資料
				ItemService itemService = new ItemService();
				String next_itemno = itemService.addItem(item_name, item_price, item_primary_class,
						item_secondary_class, item_owner, is_fb_launch, is_mall_launch, item_inventory,
						item_description);

				// 新增商品圖片
				Collection<Part> parts = req.getParts();
				ItempicService itempicSvc = new ItempicService(); // 設定要送入物件
				for (Part part : parts) {
					if (getFileNameFromPart(part) != null && part.getContentType() != null) {
						itempicSvc.insert(next_itemno, getPictureByteArray(part.getInputStream()));
						System.out.println("新增圖片成功爽一波");

					}
				}

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = requestURL + "?whichPage=99999";
				req.setAttribute("item_no",next_itemno);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("新增商品失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/addItem.jsp");
				failureView.forward(req, res);
			}

		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)
			

			try {
				String item_no = req.getParameter("item_no");

				String item_name = req.getParameter("item_name");
				// String item_name_Reg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9)]{1,}$";
				if (item_name == null || item_name.trim().length() == 0) {
					errorMsgs.add("商品名稱請勿空白");
				}

				Integer item_price;

				try {
					item_price = new Integer(req.getParameter("item_price").trim());
				} catch (NumberFormatException e) {
					item_price = 0;
					errorMsgs.add("價格請填數字");
				}

				Integer item_primary_class;

				try {
					item_primary_class = new Integer(req.getParameter("item_primary_class").trim());
				} catch (NumberFormatException e) {
					item_primary_class = 0;
					errorMsgs.add("主類別錯誤");
				}

				Integer item_secondary_class;

				try {
					item_secondary_class = new Integer(req.getParameter("item_secondary_class").trim());
				} catch (NumberFormatException e) {
					item_secondary_class = 0;
					errorMsgs.add("副類別錯誤");
				}

				String item_owner = req.getParameter("item_owner");
				// String item_owner_Reg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9)]{1,}$";
				// if(item_owner == null || item_owner.trim().length() == 0) {
				// errorMsgs.add("商品擁有者請勿空白");
				// }

				Integer is_fb_launch;

				try {
					is_fb_launch = new Integer(req.getParameter("is_fb_launch").trim());

				} catch (NumberFormatException e) {
					is_fb_launch = 0;
					errorMsgs.add("FB上下架錯誤");
				}

				Integer is_mall_launch;

				try {
					is_mall_launch = new Integer(req.getParameter("is_mall_launch").trim());
				} catch (NumberFormatException e) {
					is_mall_launch = 0;
					errorMsgs.add("商城上下架錯誤");
				}

				Integer item_inventory;

				try {
					item_inventory = new Integer(req.getParameter("item_inventory").trim());
				} catch (NumberFormatException e) {
					item_inventory = 0;
					errorMsgs.add("庫存請輸入數字");
				}

				String item_description = req.getParameter("item_description");
				// String item_description_Reg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9)]{1,}$";
				if (item_description == null || item_description.trim().length() == 0) {
					errorMsgs.add("商品描述請勿空白");
				}

				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (getFileNameFromPart(part) != null && part.getContentType() != null) {
						ItempicService itempicSvc = new ItempicService(); // 設定要送入物件
						itempicSvc.insert(item_no, getPictureByteArray(part.getInputStream()));
						System.out.println("新增圖片成功爽一波");

					}
				}

				ItemVO itemVO = new ItemVO();

				itemVO.setItem_no(item_no);
				itemVO.setItem_name(item_name);
				itemVO.setItem_price(item_price);
				itemVO.setItem_primary_class(item_primary_class);
				itemVO.setItem_secondary_class(item_secondary_class);
				itemVO.setItem_owner(item_owner);
				itemVO.setIs_fb_launch(is_fb_launch);
				itemVO.setIs_mall_launch(is_mall_launch);
				itemVO.setItem_inventory(item_inventory);
				itemVO.setItem_description(item_description);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("itemVO", itemVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}

				ItemService itemService = new ItemService();
				itemVO = itemService.updateItem(item_no, item_name, item_price, item_primary_class,
						item_secondary_class, item_owner, is_fb_launch, is_mall_launch, item_inventory,
						item_description);

				String url = requestURL + "?whichPage=" + whichPage; // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)和修改的是哪一筆
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改商品失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}

		}

		if ("recall".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
																// 【/dept/listEmps_ByDeptno.jsp】 或 【
																// /dept/listAllDept.jsp】
			String whichPage = req.getParameter("whichPage"); // 送出刪除的來源網頁的第幾頁(只用於:istAllEmp.jsp)

			try {
				String item_no = req.getParameter("item_no");

				ItemService itemService = new ItemService();
				LimitSaleService limitSaleSvc = new LimitSaleService();
				
				if(req.getParameter("sale_no") != null)
				limitSaleSvc.offLimit(Integer.parseInt(req.getParameter("sale_no")));
				itemService.updateLaunch(item_no, 0, 0);

				String url = requestURL + "?whichPage=" + whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("下架資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}

		}

		if ("deletePic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			List<ItempicVO> itempicVO = null;

			String requestURL = (String) req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或
			// 【/dept/listEmps_ByDeptno.jsp】 或 【
			// /dept/listAllDept.jsp】
			req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req

			String whichPage = (String) req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			req.setAttribute("whichPage", whichPage);
			
			try {
				String item_pic_no = req.getParameter("item_pic_no");
				String itemno = req.getParameter("item_no");

				ItempicService itempicService = new ItempicService();

				itempicVO = itempicService.getOneItemAllPic(itemno);
				if (itempicVO.size() == 1) {
					errorMsgs.add("商品圖片剩最後一張，已無法再刪除");
				} else
					itempicService.delete(item_pic_no);

				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemno);
				req.setAttribute("itemVO", itemVO);

				String url = "/FrontEnd/item/update_item.jsp";
				// res.sendRedirect(url);

				// String url = "/FrontEnd/item/update_item.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}

		}

		if ("itemPicShow".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String itemno = req.getParameter("item_no");

				/*************************** 2.開始查詢資料 *****************************************/
				ItemService itemSvc = new ItemService();
				ItemVO itemVO = itemSvc.getOneItem(itemno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("itemVO", itemVO);

				// Bootstrap_modal
				boolean openModal = true;
				req.setAttribute("openModal", openModal);
				String url = "/FrontEnd/item/ItemAll.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/item/ItemAll.jsp");
				failureView.forward(req, res);
			}
		}

	}

	// 使用byte[]方式
	public static byte[] getPictureByteArray(InputStream ips) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = ips.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();

		return baos.toByteArray();
	}

	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);

		if (filename.length() == 0) {
			return null;
		}
		return filename;

	}

}

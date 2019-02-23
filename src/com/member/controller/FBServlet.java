package com.member.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import com.chat.model.ChatService;
import com.item.model.ItemVO;
import com.member.model.*;
import com.randomNewItem.model.RandomItemService;
import com.shoppingCart.model.FbShoppingCartVO;

@MultipartConfig()
@WebServlet("/FrontEnd/member/fb.do")
public class FBServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		// 登出開始
		HttpSession session = req.getSession();

		if ("logout".equals(action)) {
			session.removeAttribute("memVO"); // code inserted by Amber
			session.removeAttribute("shoppingcart");
			res.sendRedirect(req.getContextPath() + "/FrontEnd/index.jsp");
		}
		// 登出結束

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		List<FbShoppingCartVO> fb_buylist = (Vector<FbShoppingCartVO>) session.getAttribute("fb_shoppingCart");

		// FB登入
		if ("fb_login".equals(action)) {

			// 取得FB ID
			String mem_id = req.getParameter("mem_id");
			System.out.println("FB ID：" + mem_id);
			// 給預設密碼
			String mem_psw = "123456";
			System.out.println("FB PSW：" + mem_psw);

			// 取得FB 名字，取到的中文編碼FB API預設為UTF-8，轉成編輯器的工作環境編碼
			String mem_name1 = req.getParameter("mem_name");
			byte[] ms950Bytes = mem_name1.getBytes("MS950");
			String mem_name = new String(ms950Bytes, "MS950");
			System.out.println("FB NAME：" + mem_name);

			MemDAO dao = new MemDAO();
			MemVO memCheckVO = dao.checkFBName(mem_name);

			if (memCheckVO == null) { // 如果FB沒有註冊過---先註冊

				// 取得FB E-MAIL
				String mem_email = req.getParameter("mem_email");
				System.out.println("FB EMAIL：" + mem_email);

				String mem_fbid = mem_id;

				// 取得FB 大頭貼
				String picurl = req.getParameter("mem_picture");
				System.out.println("FB PIC SRC：" + picurl);
				byte[] mem_profilepic = getImageFromNetByUrl(picurl);

				// 給預設賣場封面
				@SuppressWarnings("deprecation")
				File martcover = new File(req.getRealPath("/FrontEnd/images/NewMember/mart_pic.png"));
				BufferedImage martcoverImage = ImageIO.read(martcover);
				ByteArrayOutputStream martcoverBaos = new ByteArrayOutputStream();
				ImageIO.write(martcoverImage, "png", martcoverBaos);
				martcoverBaos.flush();
				byte[] mem_martcover = martcoverBaos.toByteArray();

				MemService memSvc = new MemService();
				MemVO memFBVO = memSvc.fbSignup(mem_id, mem_name, mem_psw, mem_email, mem_fbid, mem_profilepic,
						mem_martcover);

				ChatService chatSvc = new ChatService();
				chatSvc.addFrined(memSvc.findNewestMember(), "E00000");
			} else if (memCheckVO.getMem_email() == null || memCheckVO.getMem_fbid() == null) {
				// 如果是FB得標者(FB快速註冊)，把完整FB資訊加入資料庫

				// 取得FB E-MAIL
				String mem_email = req.getParameter("mem_email");

				// 取得FB ID
				String mem_fbid = mem_id;

				// 取得FB 大頭貼
				String picurl = req.getParameter("mem_picture");
				System.out.println("FB PIC SRC：" + picurl);
				byte[] mem_profilepic = getImageFromNetByUrl(picurl);

				MemService memSvc = new MemService();
				MemVO memFbFullInfoVO = memSvc.fbFullInfo(mem_name, mem_id, mem_email, mem_fbid, mem_profilepic);
			} else {
				// 每次登入都修改圖片>.^(就是任性)
				// 取得FB 大頭貼
				String picurl = req.getParameter("mem_picture");
				System.out.println("FB PIC SRC：" + picurl);
				byte[] mem_profilepic = getImageFromNetByUrl(picurl);

				MemService memSvc = new MemService();
				MemVO memFbPictureVO = memSvc.fbNamePicture(mem_name, mem_profilepic);
			}

			// 如果FB已經註冊過---相當於登入
			MemVO memVO = allowFbUser(mem_name, mem_psw);
			String mem_Id = memVO.getMem_id();
			String session_id = session.getId();
			session.setAttribute(session_id, mem_Id); // *工作1: 才在session內做已經登入過的標識

			session.setAttribute("login_state", true); // 會員登入的狀態為true

			session.setAttribute("memVO", memVO); // session包著會員資料走

			/* 8/16 從MemVO搬過來 */
			RandomItemService riSvc = new RandomItemService();

			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {

			}
			res.sendRedirect(req.getContextPath() + "/FrontEnd/index.jsp"); // *工作3: (-->如無來源網頁:則重導至首頁)
		}

		/***************************
		 * FB直播得標者快速註冊
		 ***************************************/
		// 8/14 FB會員非InstaBuy會員快速註冊 (For CY)
		if ("fb_register".equals(action)) {
			PrintWriter out = res.getWriter();
			// 取得FB 名字，取到的中文編碼FB API預設為UTF-8，轉成編輯器的工作環境編碼
			String mem_name1 = req.getParameter("fb_buyer");
			String fb_id = req.getParameter("fb_id");
			byte[] ms950Bytes = mem_name1.getBytes("MS950");
			String mem_name = new String(ms950Bytes, "MS950");

			MemDAO dao = new MemDAO();
			MemVO memCheckVO = dao.checkFBName(mem_name);

			if (memCheckVO == null) { // 如果這位FB買家還沒註冊InstaBuy，強制註冊

				// FB買家的ID(用FB_ID 取代)
				String mem_id = fb_id;

				// 給預設密碼
				String mem_psw = "123456";
				System.out.println("FB PSW：" + mem_psw);

				// FB買家的EMAIL
				String mem_email = "FB buyer";

				// FB買家的FBID
				String mem_fbid = fb_id;

				// 給預設大頭貼
				@SuppressWarnings("deprecation")
				File profilepic = new File(req.getRealPath("/FrontEnd/images/NewMember/member_pic.png"));
				BufferedImage profilepicImage = ImageIO.read(profilepic);
				ByteArrayOutputStream profilepicBaos = new ByteArrayOutputStream();
				ImageIO.write(profilepicImage, "png", profilepicBaos);
				profilepicBaos.flush();
				byte[] mem_profilepic = profilepicBaos.toByteArray();
				System.out.println(mem_profilepic);
				profilepicBaos.close();

				// 給預設賣場封面
				@SuppressWarnings("deprecation")
				File martcover = new File(req.getRealPath("/FrontEnd/images/NewMember/mart_pic.png"));
				BufferedImage martcoverImage = ImageIO.read(martcover);
				ByteArrayOutputStream martcoverBaos = new ByteArrayOutputStream();
				ImageIO.write(martcoverImage, "png", martcoverBaos);
				martcoverBaos.flush();
				byte[] mem_martcover = martcoverBaos.toByteArray();

				MemService memSvc = new MemService();
				MemVO memFBVO = memSvc.fbSignup(mem_id, mem_name, mem_psw, mem_email, mem_fbid, mem_profilepic,
						mem_martcover);
			}

			/***************************
			 * 以下CY新增訂單的方法
			 ***************************************/
			// 取得後來新增的商品
			FbShoppingCartVO aItem = getItem(req);

			if (fb_buylist == null) {
				fb_buylist = new Vector<FbShoppingCartVO>();
				fb_buylist.add(aItem);
				out.println("購物車新增成功");
			}

			else {
				if (fb_buylist.contains(aItem)) {
					FbShoppingCartVO innerItem = fb_buylist.get(fb_buylist.indexOf(aItem));
					innerItem.setQuantity(innerItem.getQuantity() + aItem.getQuantity());
				} else {
					fb_buylist.add(aItem);
				}
			}

			for (FbShoppingCartVO i : fb_buylist)
				
				System.out.println(i.getFb_item_no() + " " + i.getFb_item_price() + " " + i.getQuantity() + " "
						+ i.getFb_buyer() + " " + i.getFb_comment() + " " + i.getFb_buyer_no()

				);
				System.out.println("----------------------------");
		}
		session.setAttribute("fb_shoppingCart", fb_buylist);

	}

	// 8/11 Hugh新增：將網路上的圖片SRC轉成byte[]
	public static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			byte[] btImg = readInputStream(inStream);
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 8/11 Hugh新增：byte[]接 ByteArrayOutputStream
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	// 8/14 Hugh新增：FB登入用名字登入
	private MemVO allowFbUser(String mem_name, String mem_psw) {

		MemService memSvc = new MemService();
		MemVO memVO = memSvc.fbLogin(mem_name, mem_psw);
		if (memVO != null) {
			return memVO;
		} else {
			return null;
		}
	}

	// 8/15購物車專用
	private FbShoppingCartVO getItem(HttpServletRequest req) {

		MemService memSvc = new MemService();

		Integer quantity = 1;// req.getParameter("quantity");
		String fb_buyer = req.getParameter("fb_buyer");
		String fb_comment = req.getParameter("fb_comment");
		String fb_item_no = req.getParameter("fb_item_no");
		String fb_buyer_no = memSvc.checkFBName(fb_buyer).getMem_no();
		Integer fb_item_price = new Integer(req.getParameter("fb_item_price"));
		String fb_item_pic = req.getParameter("fb_pic");

		FbShoppingCartVO fbShoppingCartVO = new FbShoppingCartVO();

		fbShoppingCartVO.setQuantity(quantity);
		fbShoppingCartVO.setFb_buyer(fb_buyer);
		fbShoppingCartVO.setFb_comment(fb_comment);
		fbShoppingCartVO.setFb_buyer_no(fb_buyer_no);
		fbShoppingCartVO.setFb_item_no(fb_item_no);
		fbShoppingCartVO.setFb_item_price(fb_item_price);
		fbShoppingCartVO.setFb_item_pic(fb_item_pic);

		return fbShoppingCartVO;
	}

}
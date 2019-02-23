package com.member.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import com.member.model.*;
import com.randomNewItem.model.RandomItemService;

import redis.clients.jedis.Jedis;

import com.chat.model.ChatService;
import com.mail.model.*;

@MultipartConfig()
@WebServlet("/FrontEnd/member/member.do")
public class MemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
		
		
		req.setCharacterEncoding("UTF-8");
	    res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		//登出開始
		HttpSession session = req.getSession();
	    
		if("logout".equals(action)) {
			session.removeAttribute("memVO"); //code inserted by Amber
			session.removeAttribute("shoppingCart");
			res.sendRedirect(req.getContextPath()+"/FrontEnd/index.jsp");
		}
		//登出結束
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
	    res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		
		
		// 8/7 Hugh 會員註冊更新版
		if ("signup".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String mem_id = req.getParameter("mem_id");
				String mem_idReg = "^[a-zA-Z0-9_]{3,10}$";
				MemDAO dao = new MemDAO();
				MemVO memCheckVO = dao.checkID(mem_id);
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.put("mem_id","帳號: 請勿空白");
				} else if(!mem_id.trim().matches(mem_idReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("mem_id","帳號: 只能是英文字母、數字和_ , 且長度必需在3到10之間");
	            } else if (memCheckVO != null) {
					errorMsgs.put("mem_id","此帳號已被註冊過，請重新註冊其他帳號");
				}
				
				
				String mem_name1 = req.getParameter("mem_name");
				byte[] ms950Bytes = mem_name1.getBytes("MS950"); 
				String mem_name = new String(ms950Bytes, "MS950");
				if (mem_name == null || mem_name.trim().length() == 0) {
					errorMsgs.put("mem_name","姓名請勿空白");
				}
				
				
				String mem_email = req.getParameter("mem_email").trim();
				String mem_emailReg = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+.[a-zA-Z]{2,4}";
				if (mem_email == null || mem_email.trim().length() == 0) {
					errorMsgs.put("mem_email","信箱請勿空白");
				} else if(!mem_email.trim().matches(mem_emailReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("mem_email","信箱必須符合'xxx@xxx.xxx'之格式");
	            }

				
//				String mem_psw = req.getParameter("mem_psw").trim();
//				String check_password = req.getParameter("check_password").trim();
//				if (mem_psw == null || mem_psw.trim().length() == 0) {
//					errorMsgs.put("mem_psw","密碼: 密碼請勿空白");
//				} else if (check_password == null || check_password.trim().length() == 0) {
//					errorMsgs.put("check_password","確認密碼: 確認密碼請勿空白");
//				} else if (!check_password.equals(mem_psw)) {
//					errorMsgs.put("check_password","確認密碼: 兩次輸入的密碼不相同，請確認是否相同");
//				}
				
				// 8/7修改
				// 自動產生亂數密碼
				String mem_psw = null;
				int count = 0;
				do
				{
					int random = (int)(Math.random()*75 + 48);
					if(random<58 || (random>=65&&random<91) || random>96)
					{
						if(count == 0) {
							mem_psw = String.valueOf((char)random);
							count++;
						}
						else {
							String psw = mem_psw;
							mem_psw = psw + String.valueOf((char)random);
							count++;
						}
					}
				}while(count<8);
			
				
				// 先給新會員預設大頭貼
				@SuppressWarnings("deprecation")
				File profilepic = new File(req.getRealPath("/FrontEnd/images/NewMember/member_pic.png"));
				BufferedImage profilepicImage = ImageIO.read(profilepic);
				ByteArrayOutputStream profilepicBaos = new ByteArrayOutputStream();
				ImageIO.write(profilepicImage, "png", profilepicBaos);
				profilepicBaos.flush();
				byte[] mem_profilepic = profilepicBaos.toByteArray();
				profilepicBaos.close();
				
				
				// 先給新會員預設賣場封面
				@SuppressWarnings("deprecation")
				File martcover = new File(req.getRealPath("/FrontEnd/images/NewMember/mart_pic.png"));
				BufferedImage martcoverImage = ImageIO.read(martcover);
				ByteArrayOutputStream martcoverBaos = new ByteArrayOutputStream();
				ImageIO.write(martcoverImage, "png", martcoverBaos);
				martcoverBaos.flush();
				byte[] mem_martcover = martcoverBaos.toByteArray();
				martcoverBaos.close();
				
				//新註冊會員包成VO
				MemVO memVO = new MemVO();
				memVO.setMem_id(mem_id);
				memVO.setMem_psw(mem_psw);
				memVO.setMem_email(mem_email);
				memVO.setMem_profilepic(mem_profilepic);
				memVO.setMem_martcover(mem_martcover);
		

				// 如果沒有錯誤就不會進到下面的判斷式
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的memVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/signup.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				//隨機產生驗證碼存到Redis ( mem_id 對應 mem_activecode )
				Jedis jedis = new Jedis("localhost", 6379);
				String mem_activecode = UUID.randomUUID().toString().replace( "-" , "" );
				jedis.set(mem_id, mem_activecode);
				jedis.expire(mem_id, 30);
				jedis.close();
						
				
				
				/***************************2.開始新增資料***************************************/
				//系統自動生成信件(MailService.java)
				//信箱主旨
				String subjectMS950 = "InstaBuy signup successfully !";
				byte[] utf8Bytes = subjectMS950.getBytes("UTF-8"); 
				String subject = new String(utf8Bytes, "UTF-8");
				
				//信箱內容--提供密碼及驗證碼連結
				String messageText =
						"親愛的\t" + mem_id + "，您已於InstaBuy註冊完成！<br>" + 
						"InstaBuy為您提供的密碼為： " + mem_psw + "<br>" + 
						"<a href='" + 
							req.getRequestURL() + "?action=active" + 
							"&mem_activecode=" + mem_activecode + 
							"&mem_id=" + mem_id +
							"&mem_email=" + mem_email +
							"\'>點我啟動InstaBuy帳號"+ 
						"</a><br>" + 
						"請謹慎使用！";
						//連結會導向MemServlet.java(if ("active".equals(action)))
				
				//建立MailService
				MailService mailSvc = new MailService();
				System.out.println("信箱服務OK");
				
				//系統寄信
				mailSvc.sendMail(mem_email, subject, messageText);
				System.out.println("系統已成功寄信");
				
				//新會員註冊
				MemService memSvc = new MemService();
				memVO = memSvc.signup(
						mem_id,
						mem_name,
						mem_psw, 
						mem_email,
						mem_profilepic, 
						mem_martcover
				);
				
				ChatService chatSvc = new ChatService();
				chatSvc.addFrined(memSvc.findNewestMember(), "E00000");

				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/FrontEnd/member/signupSuccess.jsp";
				res.sendRedirect(req.getContextPath()+url);
				
			/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/member/signup.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		// 8/8 Hugh 信箱驗證(驗證碼存redis)
		if ("active".equals(action)) {
		/***************************1.接收請求參數*****************************************/
			Jedis jedis = new Jedis("localhost", 6379);
			String mem_activecode = req.getParameter("mem_activecode");
			String mem_id = req.getParameter("mem_id");
			String mem_email = req.getParameter("mem_email");
			String tempAuth = jedis.get(mem_id);
			if (tempAuth == null) {
				//重新產生驗證碼
				mem_activecode = UUID.randomUUID().toString().replace( "-" , "" );
				jedis.set(mem_id, mem_activecode);
				jedis.expire(mem_id, 120);
				jedis.close();

				//系統自動生成信件(MailService.java)
				//信箱主旨
				String subjectMS950 = "InstaBuy註冊成功通知信";
				byte[] utf8Bytes = subjectMS950.getBytes("UTF-8"); 
				String subject = new String(utf8Bytes, "UTF-8");
				
				//信箱內容--提供新驗證碼連結
				String messageText =
						"親愛的\t" + mem_id + "\t您好<br>" + 
						"InstaBuy系統已為您建立新的驗證連結<br>" +  
						"<a href='" + 
							req.getRequestURL() + "?action=active" + 
							"&mem_activecode=" + mem_activecode + 
							"&mem_id=" + mem_id +
							"&mem_email=" + mem_email +
							"\'>點我啟動InstaBuy帳號"+ 
						"</a>";
						//連結會導向MemServlet.java(if ("active".equals(action)))
				
				//建立MailService
				MailService mailSvc = new MailService();
				
				//系統寄信
				mailSvc.sendMail(mem_email, subject, messageText);
				System.out.println("系統已成功寄信");
				
				/*****************************寄信完成,準備轉交(Send the Success view)***********/
				String url = "/FrontEnd/member/activeLate.jsp";
				res.sendRedirect(req.getContextPath()+url);
				
			}
			else if (mem_activecode.equals(tempAuth)){
				System.out.println("驗證成功!");
				MemVO memVO = new MemVO();
				memVO.setMem_id(mem_id);
				
				/***************************2.開始修改資料*****************************************/
				MemService memSvc = new MemService();
				memVO = memSvc.activeMem(mem_id);
				
				/***************************3.修改完成,準備轉交*************************************/
				String url = "/FrontEnd/member/activeSuccess.jsp";
				res.sendRedirect(req.getContextPath()+url);
				System.out.println("激活成功");
				jedis.close();
			} 
			
		}
		
		
		//修澤一般會員登入
		if("login".equals(action)) {
			  
			// 【取得使用者 帳號(account) 密碼(password)】
			String mem_id = req.getParameter("mem_id");
			String mem_psw = req.getParameter("mem_psw");
			Map<String, String> errorMsgs = new HashMap<String,String>();
			
			// 【檢查該帳號 , 密碼是否有效】
			MemVO memVO = allowUser(mem_id,mem_psw);
			if (memVO == null) {          //【帳號 , 密碼無效時】
				errorMsgs.put("mem_id","帳號或密碼錯誤！請重新輸入");
				req.setAttribute("errorMsgs", errorMsgs);
				String url = "/FrontEnd/member/login.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req,res);
				
			}else if(memVO.getMem_condition()==0) {
				errorMsgs.put("mem_id","信箱驗證未通過！請至信箱點擊驗證連結");
				req.setAttribute("errorMsgs", errorMsgs);
				String url = "/FrontEnd/member/login.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req,res);
				
			}else {  
			     //【帳號 , 密碼有效時, 才做以下工作】
				HttpSession session = req.getSession();
				String mem_Id = memVO.getMem_id();
				String session_id = session.getId();
				session.setAttribute(session_id, mem_Id);  //*工作1: 才在session內做已經登入過的標識
				session.setAttribute("login_state",true);  //會員登入的狀態為true
				session.setAttribute("memVO", memVO);  //session包著會員資料走
				
				
				/* 8/16 從MemVO搬過來 */
				RandomItemService riSvc = new RandomItemService();
				int random = (1 + (int)Math.random()*6);
		        session.setAttribute("preference", riSvc.getRandomFiveItemsByCategory(random));
		        session.setAttribute("CLOTH_CNTS", 0);
		        session.setAttribute("FOOD_CNTS", 0);
		        session.setAttribute("GAME_CNTS", 0);
		        session.setAttribute("OUTDOOR_CNTS", 0);
		        session.setAttribute("HOMEELEC_CNTS", 0);
		        session.setAttribute("THREEC_CNTS", 0);
		        
				
		        
		        
		        
				try {                                                        
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
						res.sendRedirect(location);   
						return;
					}
				}catch (Exception ignored) {
				}
				res.sendRedirect(req.getContextPath()+"/FrontEnd/index.jsp");  //*工作3: (-->如無來源網頁:則重導至首頁)
			}
		}
		
		
		// 8/7 會員自己修改會員資料
		  if ("updateMem".equals(action)) { 
			  Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
				req.setAttribute("errorMsgs", errorMsgs);
				byte[] mem_profilepic = null;
				ByteArrayOutputStream baos=null;

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					
					HttpSession session = req.getSession();
					MemVO memVO = (MemVO)session.getAttribute("memVO");
					String mem_no ="";
					if(memVO != null) {
						mem_no = memVO.getMem_no();
					}

					String mem_name = req.getParameter("mem_name").trim();

					if (mem_name == null || mem_name.trim().length() == 0) {
						errorMsgs.put("name","會員姓名請勿空白");
					}				

					String mem_email = req.getParameter("mem_email").trim();
					String mem_emailReg = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9._%-]+.[a-zA-Z]{2,4}";
					if (mem_email == null || mem_email.trim().length() == 0) {
						errorMsgs.put("email","信箱請勿空白");
					} else if(!mem_email.trim().matches(mem_emailReg)) { //以下練習正則(規)表示式(regular-expression)
						errorMsgs.put("email","信箱必須符合'xxx@xxx.xxx'之格式");
		            }

					String mem_mobile = req.getParameter("mem_mobile").trim();
					String mem_PhoneReg = "^(0)(9)([0-9]{8})$";
					if (mem_mobile == null || mem_mobile.trim().length() == 0) {
						errorMsgs.put("mobile","會員手機請勿空白");
					} 
					else if(!(mem_mobile.trim()).matches(mem_PhoneReg)) { //以下練習正則(規)表示式(regular-expression)
						errorMsgs.put("mobile","需為09開頭, 且長度必需10碼的數字");
		            }

					String mem_sex = req.getParameter("mem_sex");

					String mem_post = req.getParameter("mem_post");
					if(mem_post != null) {
						if(mem_post.length() != 3) {
							errorMsgs.put("post","郵遞區號只能為三碼");
						}
					}
					String mem_address = req.getParameter("mem_address");

					
					java.sql.Date mem_birth = null; 				
					try {
						mem_birth = java.sql.Date.valueOf(req.getParameter("mem_birth").trim());
					    } catch (IllegalArgumentException e) {
					    mem_birth = null;
					    }
					
					Part mem_photo = req.getPart("mem_profilepic");
					String fileName = getFileNameFromPart(mem_photo);
					String fileMimeType = getServletContext().getMimeType(fileName);
					
					List<String> mimeTypeList = new ArrayList<>();
					mimeTypeList.add("image/jpeg");
					mimeTypeList.add("image/bmp");
					mimeTypeList.add("image/png");
					mimeTypeList.add("image/gif");
					if(mem_photo.getSize() == 0) {
						if(getFileNameFromPart(mem_photo) == null) {
							MemService memSvc = new MemService();
							MemVO memVO_old = memSvc.findByPK(mem_no);
							mem_profilepic = memVO_old.getMem_profilepic();
							System.out.println("用舊圖");
						}
						
					}else if( mimeTypeList.indexOf(fileMimeType) < 0 ) {
						errorMsgs.put("photo","圖片格式不符(.jpg/jpeg/bmp/gif/png)。");
					}
					
					
					
					memVO.setMem_name(mem_name);
					memVO.setMem_email(mem_email);
					memVO.setMem_mobile(mem_mobile);
					memVO.setMem_sex(mem_sex);
					memVO.setMem_post(mem_post);
					memVO.setMem_address(mem_address);
					memVO.setMem_birth(mem_birth);
					memVO.setMem_profilepic(mem_profilepic);
					
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的VO物件,也存入req
						System.out.println("修改失敗");	
						
						RequestDispatcher failureView = req
								.getRequestDispatcher("/FrontEnd/member/updateProfile.jsp");
						failureView.forward(req, res);
						return; //程式中斷
						
					}
					
					/***************************2.開始修改資料*****************************************/
				
					if(getFileNameFromPart(mem_photo) != null) {
						InputStream is = mem_photo.getInputStream();
						baos = new ByteArrayOutputStream();
						byte[] buf = new byte[is.available()];
						
						int len = 0 ;
						while((len = is.read(buf))!=-1) {
							baos.write(buf,0,len);
						}
						baos.close();
						is.close();
						
						mem_profilepic = baos.toByteArray();
						
						//將修改照片後的encode寫進VO
						String profilepicEncoded_new = Base64.getEncoder().encodeToString(mem_profilepic);			
						memVO.setProfilepicEncoded(profilepicEncoded_new);
					}
			
					MemService memSvc = new MemService();
					memSvc.updateMem(mem_email, mem_name, mem_mobile, mem_sex, mem_post, mem_address, mem_birth, mem_profilepic, mem_no);

					
					/***************************3.修改完成,準備轉交(Send the Success view)*************/
					session.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的memVO物件,存入req
					
					String url = "/FrontEnd/member/profile.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
					successView.forward(req, res);
					System.out.println("修改成功");
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.put("Exception",e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/profile.jsp");
					failureView.forward(req, res);
				}
			}

		  
		  
		  
		// 8/7 會員修改密碼
		  if ("updatePsw".equals(action)) { // profile.jsp的請求
	        	System.out.println("修改密碼");
	        	Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					
					HttpSession session = req.getSession();
					MemVO memVO = (MemVO)session.getAttribute("memVO");
					String mem_no ="";
					if(memVO != null) {
						mem_no = memVO.getMem_no();
					}

					String mem_psw = req.getParameter("mem_psw").trim();
					String check_password = req.getParameter("check_password").trim();
					if (mem_psw == null || mem_psw.trim().length() == 0) {
						errorMsgs.put("psw","密碼請勿空白");
					} else if (check_password == null || check_password.trim().length() == 0) {
						errorMsgs.put("checkpsw","確認密碼請勿空白");
					} else if (!check_password.equals(mem_psw)) {
						errorMsgs.put("checkpsw","兩次輸入的密碼不相同，請確認是否相同");
					}
					memVO.setMem_psw(mem_psw);
					
					
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
						System.out.println("修改失敗");	
						
						RequestDispatcher failureView = req
								.getRequestDispatcher("/FrontEnd/member/updatePsw.jsp");
						failureView.forward(req, res);
						return; //程式中斷
						
					}
					
					/***************************2.開始修改資料*****************************************/
					
					MemService memSvc = new MemService();
					memSvc.updatePsw(mem_psw, mem_no);

					/***************************3.修改完成,準備轉交(Send the Success view)*************/
					session.removeAttribute("memVO"); // 資料庫update成功後,正確的的memVO物件,存入req
					
					String url = "/FrontEnd/member/login.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
					successView.forward(req, res);
					System.out.println("修改成功");
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.put("Exception",e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/profile.jsp");
					failureView.forward(req, res);
				}
			}
		  
		  
			// 8/7 會員自己修改賣場資料
		  
		  if ("updateStore".equals(action)) { // profile.jsp的請求
	        	System.out.println("修改");
	        	Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				byte[] mem_martcover = null;
				ByteArrayOutputStream baos=null;

				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					
					HttpSession session = req.getSession();
					MemVO memVO = (MemVO)session.getAttribute("memVO");
					String mem_no ="";
					if(memVO != null) {
						mem_no = memVO.getMem_no();
					}
					System.out.println("mem_no="+mem_no);
					String mem_martname = req.getParameter("mem_martname").trim();
					if (mem_martname == null || mem_martname.trim().length() == 0) {
						errorMsgs.put("martname","賣場名稱請勿空白");
					}	
					System.out.println("mem_martame="+mem_martname);
					String mem_martinfo = req.getParameter("mem_martinfo").trim();
					if (mem_martinfo == null || mem_martinfo.trim().length() == 0) {
						errorMsgs.put("martinfo","賣場簡介請勿空白");
					}	
			
					
					Part mart_photo = req.getPart("mem_martcover");
					String fileName = getFileNameFromPart(mart_photo);
					String fileMimeType = getServletContext().getMimeType(fileName);
					
					List<String> mimeTypeList = new ArrayList<>();
					mimeTypeList.add("image/jpeg");
					mimeTypeList.add("image/bmp");
					mimeTypeList.add("image/png");
					mimeTypeList.add("image/gif");
					
					if(getFileNameFromPart(mart_photo) == null) {
						MemService memSvc = new MemService();
						MemVO memVO_old = memSvc.findByPK(mem_no);
						mem_martcover = memVO_old.getMem_martcover();
						
					}else if( mimeTypeList.indexOf(fileMimeType) < 0 ) {
						errorMsgs.put("photo","圖片格式不符(.jpg/jpeg/bmp/gif/png)。");
					}
					
					String mem_activecode = req.getParameter("mem_activecode");
					if(mem_activecode == null || mem_activecode.trim().length() == 0) {
						mem_activecode = null;
					}
			
					
					memVO.setMem_martname(mem_martname);
					memVO.setMem_martinfo(mem_martinfo);
					memVO.setMem_martcover(mem_martcover);
					memVO.setMem_activecode(mem_activecode);
					
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的VO物件,也存入req
						System.out.println("修改失敗");	
						
						RequestDispatcher failureView = req
								.getRequestDispatcher("/FrontEnd/member/updateStoreInfo.jsp");
						failureView.forward(req, res);
						return; //程式中斷
						
					}
					
					/***************************2.開始修改資料*****************************************/
				
					if(getFileNameFromPart(mart_photo) != null) {
						InputStream is = mart_photo.getInputStream();
						baos = new ByteArrayOutputStream();
						byte[] buf = new byte[is.available()];
						
						int len = 0 ;
						while((len = is.read(buf))!=-1) {
							baos.write(buf,0,len);
						}
						baos.close();
						is.close();
						
						mem_martcover = baos.toByteArray();
						
						//將修改照片後的encode寫進VO
						String martcoverEncoded_new = Base64.getEncoder().encodeToString(mem_martcover);			
						memVO.setMartcoverEncoded(martcoverEncoded_new);
					}
					MemService memSvc = new MemService();
					memSvc.updateStore(mem_martname, mem_martinfo, mem_martcover, mem_activecode, mem_no);


					
					/***************************3.修改完成,準備轉交(Send the Success view)*************/
					session.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的memVO物件,存入req
					
					

					
					String url = "/FrontEnd/member/storeInfo.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
					successView.forward(req, res);
					System.out.println("修改成功");
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.put("Exception",e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/storeInfo.jsp");
					failureView.forward(req, res);
				}
			}
		  
		  if ("confirmPsw".equals(action)) { 
	        	System.out.println("確認密碼");
	        	Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
				
				req.setAttribute("errorMsgs", errorMsgs);
				
				try {
					/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
					
					HttpSession session = req.getSession();
					MemVO memVO = (MemVO)session.getAttribute("memVO");
					String mem_no ="";
					if(memVO != null) {
						mem_no = memVO.getMem_no();
					}

					String mem_psw = req.getParameter("mem_psw").trim();
					String check_password = memVO.getMem_psw();
					
					if (mem_psw == null || mem_psw.trim().length() == 0) {
						errorMsgs.put("psw","請輸入密碼");
					} else if (! mem_psw.equals(check_password)) {
						errorMsgs.put("psw","密碼不正確");
					}
				
					
					if (!errorMsgs.isEmpty()) {
						
						RequestDispatcher failureView = req
								.getRequestDispatcher("/FrontEnd/member/confirmPsw.jsp");
						failureView.forward(req, res);
						return; //程式中斷
						
					}
					
					/***************************準備轉交(Send the Success view)*************/
					String url = "/FrontEnd/member/updatePsw.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); 
					successView.forward(req, res);
					/***************************其他可能的錯誤處理*************************************/
				} catch (Exception e) {
					errorMsgs.put("Exception",e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/member/profile.jsp");
					failureView.forward(req, res);
				}
			}
		  
	}
	
	
	
	
	
	private MemVO allowUser(String mem_id, String mem_psw) {
		    
		MemService memSvc = new MemService(); 
		MemVO memVO = memSvc.login(mem_id,mem_psw);
		if (memVO != null) {
			return memVO;
		}else {
			return null;
		}
	}
	 
	//InputStream轉Byte
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename);  //測試用

		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
	
}
